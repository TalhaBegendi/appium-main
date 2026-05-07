#!/bin/bash

build_wda_bundle() {
  local tag="$1"
  local counter="$2"
  echo "com.mycompany.WebDriverAgentRunner.${tag}.${counter}"
}

build_appium_default_caps() {
  local wda_port="$1"
  local bundle="$2"

  if [ "$IS_ANDROID" = "true" ]; then
    echo ""
  else
    echo "{\"appium:wdaLocalPort\":$wda_port,\"appium:updatedWDABundleId\":\"$bundle\"}"
  fi
}

start_appium_server() {
  local host="$1"
  local requested_port="$2"
  local udid="$3"
  local wda_port="$4"
  local bundle="$5"
  local base_path="$6"

  local app_port
  local log_file
  local default_caps
  local cmd=()

  app_port="$(resolve_port "$host" "$requested_port")" || return 1
  log_file="$RUNTIME_DIR/appium_${udid}.log"
  default_caps="$(build_appium_default_caps "$wda_port" "$bundle")"

  if nc -z "$host" "$app_port" 2>/dev/null; then
    log "Appium already active on $host:$app_port"
    echo "$app_port"
    return 0
  fi

  log "Starting Appium on $host:$app_port ..."

  cmd=(
    appium
    --address "$host"
    --port "$app_port"
    --base-path "$base_path"
    --relaxed-security
    --session-override
  )

  if [ -n "$default_caps" ]; then
    cmd+=(--default-capabilities "$default_caps")
  fi

  "${cmd[@]}" > "$log_file" 2>&1 &
  echo "$!" >> "$APPIUM_PIDS_FILE"

  wait_for_port "$host" "$app_port" "Appium" 30 || return 1
  echo "$app_port"
}

build_stereotype() {
  local base_device="$1"
  local wda_port="$2"
  local bundle="$3"
  local platform="$4"
  local automation_name="$5"
  local udid="$6"

  if [ "$platform" = "Android" ]; then
    jq -c \
      --arg platformName "$platform" \
      --arg automationName "$automation_name" \
      --arg ud "$udid" \
      '
      del(.tag)
      + {
          "platformName": $platformName,
          "appium:automationName": $automationName,
          "appium:udid": $ud
        }
      ' <<< "$base_device"
  else
    jq -c \
      --arg wdaPort "$wda_port" \
      --arg bundle "$bundle" \
      --arg platformName "$platform" \
      --arg automationName "$automation_name" \
      --arg ud "$udid" \
      '
      del(.tag)
      + {
          "appium:wdaLocalPort": ($wdaPort|tonumber),
          "appium:updatedWDABundleId": $bundle,
          "platformName": $platformName,
          "appium:automationName": $automationName,
          "appium:udid": $ud
        }
      ' <<< "$base_device"
  fi
}