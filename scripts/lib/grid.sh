#!/bin/bash

start_grid() {
  local lock_file="$RUNTIME_DIR/grid.lock"
  local lock_dir="${lock_file}.lockdir"
  local device_count
  local total_started=0
  local i
  local base_device
  local tag
  local device_name
  local json_udid
  local udid
  local requested_app_port
  local requested_node_port
  local actual_app_port
  local actual_node_port
  local counter
  local wda_port
  local bundle
  local platform
  local automation_name
  local stereotype

  log "GRID mode enabled → Selenium Grid + Appium"

  device_count="$(get_device_count)"

  if nc -z "$GRID_HUB_HOST" "$GRID_HUB_PORT" >/dev/null 2>&1; then
    log "Hub port $GRID_HUB_PORT already active, synchronizing lock state"
    echo "started" > "$lock_file"
  else
    rm -f "$lock_file"
  fi

  if mkdir "$lock_dir" 2>/dev/null; then
    if [ ! -f "$lock_file" ] || ! grep -q "started" "$lock_file"; then
      log "Starting Selenium Grid Hub on $GRID_HUB_URL ..."
      java -jar "$SELENIUM_JAR" hub --port "$GRID_HUB_PORT" > "$RUNTIME_DIR/hub.log" 2>&1 &
      echo "$!" > "$HUB_PID_FILE"

      if wait_for_port "$GRID_HUB_HOST" "$GRID_HUB_PORT" "Hub" 30; then
        echo "started" > "$lock_file"
        ok "Hub ready on $GRID_HUB_PORT"
      else
        err "Hub startup failed — cleaning lock"
        rm -f "$lock_file"
        if [ -f "$HUB_PID_FILE" ]; then
          kill -9 "$(cat "$HUB_PID_FILE")" 2>/dev/null || true
        fi
        rmdir "$lock_dir" 2>/dev/null || true
        exit 1
      fi
    else
      log "Grid Hub already active (detected via grid.lock)"
    fi

    rmdir "$lock_dir"
  else
    log "Grid Hub is already being initialized by another process."
  fi

  boot_requested_emulators

  for i in $(seq 0 $((device_count - 1))); do
    base_device="$(get_device_json "$i")"
    validate_device_json "$base_device" || continue

    tag="$(get_device_tag "$base_device")"
    device_name="$(get_device_name "$base_device")"
    json_udid="$(get_device_udid_from_json "$base_device")"

   is_requested_tag "$tag" || continue

    if [ "$total_started" -ge "$MAX_TOTAL_DEVICES" ]; then
      warn "Reached MAX_TOTAL_DEVICES=$MAX_TOTAL_DEVICES"
      break
    fi

    if ! udid="$(resolve_device_udid "$device_name" "$json_udid" "$tag")"; then
      continue
    fi

    requested_app_port=$((APPIUM_BASE_PORT + i + PLATFORM_PORT_OFFSET))
    requested_node_port=$((GRID_START_NODE_PORT + i + PLATFORM_PORT_OFFSET))
    actual_node_port="$(resolve_port "$GRID_HUB_HOST" "$requested_node_port")" || continue

    if [ "$IS_ANDROID" = "true" ]; then
      platform="Android"
      automation_name="UiAutomator2"
      wda_port=0
      bundle=""
    else
      platform="iOS"
      automation_name="XCUITest"
      counter="$(next_wda_counter)"
      wda_port=$((WDA_BASE_PORT + counter))
      bundle="$(build_wda_bundle "$tag" "$counter")"
    fi

    log "Preparing device tag=$tag ($device_name, UDID=$udid)"

    if ! actual_app_port="$(start_appium_server "$GRID_HUB_HOST" "$requested_app_port" "$udid" "$wda_port" "$bundle" "$GRID_BASE_PATH")"; then
      continue
    fi

    update_port_map "$tag" "$udid" "$actual_app_port"

    stereotype="$(build_stereotype "$base_device" "$wda_port" "$bundle" "$platform" "$automation_name" "$udid")"

    log "Registering Node on $actual_node_port with stereotype: $stereotype"

    java -jar "$SELENIUM_JAR" node \
      --detect-drivers false \
      --port "$actual_node_port" \
      --service-url "http://$GRID_HUB_HOST:$actual_app_port/wd/hub" \
      --service-host "$GRID_HUB_HOST" \
      --service-port "$actual_app_port" \
      --service-status-endpoint /status \
      --service-configuration max-sessions="$MAX_SESSIONS" stereotype="$stereotype" \
      > "$RUNTIME_DIR/node_${udid}.log" 2>&1 &

    echo "$!" >> "$NODE_PIDS_FILE"

    wait_for_port "$GRID_HUB_HOST" "$actual_node_port" "Node" 30 || continue

    ok "Node ready for $tag on port=$actual_node_port"
    total_started=$((total_started + 1))
  done
  log "Hub UI: $GRID_HUB_URL/ui"
}