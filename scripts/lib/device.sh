#!/bin/bash

get_device_count() {
  jq length "$DEVICE_FILE"
}

get_device_json() {
  local index="$1"
  jq -c ".[$index]" "$DEVICE_FILE"
}

get_device_tag() {
  jq -r '.tag' <<< "$1"
}

get_device_name() {
  jq -r '."appium:deviceName"' <<< "$1"
}

get_device_udid_from_json() {
  jq -r '."appium:udid"' <<< "$1"
}

validate_device_json() {
  local base_device="$1"
  local tag device_name

  tag="$(get_device_tag "$base_device")"
  device_name="$(get_device_name "$base_device")"

  [ -n "$tag" ] && [ "$tag" != "null" ] || { err "Device JSON missing .tag"; return 1; }
  [ -n "$device_name" ] && [ "$device_name" != "null" ] || { err "Device JSON missing appium:deviceName for tag=$tag"; return 1; }

  return 0
}

adb_devices_safe() {
  adb devices 2>/dev/null && return 0
  sleep 1
  adb devices 2>/dev/null && return 0
  err "adb devices failed"
  return 1
}

normalize() {
  echo "$1" | tr '[:upper:]' '[:lower:]' | tr -d '_ '
}

resolve_android_udid_by_avd() {
  local avd_name="$1"
  local d
  local avd

  for d in $(adb_devices_safe | awk 'NR>1 && $1 ~ /emulator-/ {print $1}'); do
    avd=$(adb -s "$d" emu avd name 2>/dev/null | tr -d '\r\n' | sed 's/OK$//' | xargs)
    if [ "$(normalize "$avd")" = "$(normalize "$avd_name")" ]; then
      echo "$d"
      return 0
    fi
  done

  echo ""
}

ensure_android_emulator() {
  local tag="$1"
  bash "$EMULATOR_SCRIPT" "$tag"
}

resolve_device_udid() {
  local device_name="$1"
  local json_udid="$2"
  local tag="$3"

  if [ "$IS_ANDROID" = "true" ]; then
    if [ -n "$json_udid" ] && [ "$json_udid" != "null" ]; then
      echo "$json_udid"
      return 0
    fi

    local udid
    udid=$(resolve_android_udid_by_avd "$device_name")

    if [ -z "$udid" ]; then
      err "Cannot resolve Android UDID for tag=$tag deviceName=$device_name"
      return 1
    fi

    echo "$udid"
    return 0
  fi

  if [ -z "$json_udid" ] || [ "$json_udid" = "null" ]; then
    err "Missing iOS UDID for tag=$tag deviceName=$device_name"
    return 1
  fi

  echo "$json_udid"
}

boot_requested_emulators() {
  [ "$IS_ANDROID" = "true" ] || return 0

  local count
  local i
  local base
  local tag
  local udid
  local device_name

  count="$(get_device_count)"

  for i in $(seq 0 $((count - 1))); do
    base="$(get_device_json "$i")"
    validate_device_json "$base" || continue

    tag="$(get_device_tag "$base")"
    udid="$(get_device_udid_from_json "$base")"
    device_name="$(get_device_name "$base")"

    is_requested_tag "$tag" || continue

    if [ -n "$udid" ] && [ "$udid" != "null" ]; then
      log "Android device with predefined UDID detected for tag=$tag. Skipping emulator launch."
      continue
    fi

    ensure_android_emulator "$tag" || {
      err "Emulator failed for tag=$tag"
      exit 1
    }
  done
}