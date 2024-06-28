#!/usr/bin/env bash
set -euo pipefail

cd weather-tests/
mvn test

# Uncomment to be able to attach and debug container
# sleep infinity