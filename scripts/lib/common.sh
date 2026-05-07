#!/bin/bash

log()  { echo "ℹ️ $*" >&2; }
ok()   { echo "✅ $*" >&2; }
warn() { echo "⚠️ $*" >&2; }
err()  { echo "❌ $*" >&2; }

parse_args() {
  IS_ANDROID="${1:-}"
  REQUESTED_TAGS="${2:-""}"

  [ -z "$IS_ANDROID" ] && { err "Missing parameter: IS_ANDROID (true/false)"; exit 1; }

  case "$IS_ANDROID" in
    true)  PLATFORM_PORT_OFFSET=$ANDROID_PORT_OFFSET ;;
    false) PLATFORM_PORT_OFFSET=$IOS_PORT_OFFSET ;;
    *) err "IS_ANDROID must be true or false"; exit 1 ;;
  esac
}

require_cmd() {
  local cmd="$1"
  command -v "$cmd" >/dev/null 2>&1 || { err "Missing command: $cmd"; exit 1; }
}

check_dependencies() {
  require_cmd jq
  require_cmd nc
  require_cmd appium
  require_cmd java
  require_cmd sed
  require_cmd awk
  require_cmd grep
  require_cmd python3

  if [ "$IS_ANDROID" = "true" ]; then
    require_cmd adb
  fi
}

wait_for_port() {
  local host="$1"
  local port="$2"
  local name="$3"
  local timeout="${4:-30}"
  local attempt=0
  local elapsed=0
  local wait_time

  while [ "$elapsed" -lt "$timeout" ]; do
    if nc -z "$host" "$port" 2>/dev/null; then
      return 0
    fi

    wait_time=$((2 ** attempt))
    [ "$wait_time" -gt 5 ] && wait_time=5

    sleep "$wait_time"
    elapsed=$((elapsed + wait_time))
    attempt=$((attempt + 1))
  done

  err "$name not ready on $port within ${timeout}s"
  return 1
}

get_free_port() {
  python3 -c "import socket; s=socket.socket(); s.bind(('',0)); print(s.getsockname()[1]); s.close()"
}

resolve_port() {
  local host="$1"
  local preferred_port="$2"

  if nc -z "$host" "$preferred_port" 2>/dev/null; then
    if [ "$ALLOW_DYNAMIC_PORT_FALLBACK" = "true" ]; then
      warn "Preferred port $preferred_port is busy on $host, falling back to a dynamic free port"
      get_free_port
      return 0
    fi
    err "Preferred port $preferred_port is busy on $host and dynamic fallback is disabled"
    return 1
  fi

  echo "$preferred_port"
}

is_requested_tag() {
  local tag="$1"
  [ -z "$REQUESTED_TAGS" ] && return 0
  grep -qw "$tag" <<< "$REQUESTED_TAGS"
}

next_wda_counter() {
  local counter
  counter=$(cat "$WDA_COUNTER_FILE")
  counter=$((counter + 1))

  if [ "$counter" -gt "$WDA_COUNTER_MAX" ]; then
    counter=1
  fi

  echo "$counter" > "$WDA_COUNTER_FILE"
  echo "$counter"
}

update_port_map() {
  local tag="$1"
  local udid="$2"
  local app_port="$3"

  grep -v "^${tag}:${udid}=" "$PORT_MAP_FILE" > "$PORT_MAP_FILE.tmp" || true
  echo "${tag}:${udid}=${app_port}" >> "$PORT_MAP_FILE.tmp"
  mv "$PORT_MAP_FILE.tmp" "$PORT_MAP_FILE"
}