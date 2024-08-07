#!/usr/bin/env bash
set -euo pipefail

# Uncomment to be able to attach and debug container
sleep infinity

cd weather-tests/
mvn test
