#!/bin/bash

start_local() {
  local device_count
  local total_started=0
  local i
  local base_device
  local tag
  local device_name
  local json_udid
  local udid
  local requested_app_port
  local actual_app_port
  local counter
  local wda_port
  local bundle

  log "LOCAL mode enabled → Appium direct start"

  device_count="$(get_device_count)"
  log "Found $device_count base devices in $DEVICE_FILE"

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

    if [ "$IS_ANDROID" = "true" ]; then
      wda_port=0
      bundle=""
    else
      counter="$(next_wda_counter)"
      wda_port=$((WDA_BASE_PORT + counter))
      bundle="$(build_wda_bundle "$tag" "$counter")"
    fi

    if ! actual_app_port="$(start_appium_server "$LOCAL_HOST" "$requested_app_port" "$udid" "$wda_port" "$bundle" "$LOCAL_BASE_PATH")"; then
      continue
    fi

    update_port_map "$tag" "$udid" "$actual_app_port"

    ok "Appium ready for $tag on port=$actual_app_port"
    total_started=$((total_started + 1))
  done
}