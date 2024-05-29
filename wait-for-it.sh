#!/bin/bash
# Usage: ./wait-for-it.sh host:port [-s] [-t timeout] [-- command args]
# Waits for host:port to become available, or until timeout is reached

set -e

host="postgres"
port="5432"
timeout=30
cmd=""
quiet_mode=""

# Parse command line arguments
while [[ $# -gt 0 ]]
do
    case "$1" in
        --)
        shift
        cmd="$@"
        break
        ;;
        -t)
        timeout="$2"
        if [[ ! "$timeout" =~ ^[0-9]+$ ]]; then
            echo "Error: Invalid timeout value: $timeout" >&2
            exit 1
        fi
        shift 2
        ;;
        -q)
        quiet_mode="true"
        shift 1
        ;;
        *)
        echo "Error: Invalid argument: $1" >&2
        exit 1
        ;;
    esac
done

# Function to check if the port is open
wait_for_port() {
    local host="$1"
    local port="$2"
    local timeout="$3"

    local start_time=$(date +%s)
    local end_time=$((start_time + timeout))

    while true; do
        nc -z "$host" "$port" >/dev/null 2>&1
        result=$?
        if [[ $result -eq 0 ]]; then
            if [[ -n "$quiet_mode" ]]; then
                return 0
            else
                echo "Success: $host:$port is available after $(( $(date +%s) - start_time )) seconds" >&2
            fi
            return 0
        fi
        current_time=$(date +%s)
        if [[ "$current_time" -ge "$end_time" ]]; then
            echo "Error: Timeout waiting for $host:$port" >&2
            exit 1
        fi
        sleep 1
    done
}

# Execute the wait_for_port function
wait_for_port "$host" "$port" "$timeout"

# Execute the provided command, if any
exec "$cmd"
