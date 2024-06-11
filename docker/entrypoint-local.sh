#!/usr/bin/env bash
set -euo pipefail

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:8001 -jar -Dspring.profiles.active=local ./weather-tests.jar
