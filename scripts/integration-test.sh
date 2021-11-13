#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

. ./scripts/start-application.sh
. ./scripts/check-application-health.sh

echo "Application started successfully"
echo "Running integration Tests"

{
  ./gradlew :integration-test:test
} || {
  . ./scripts/stop-application.sh
  exit 0
}

. ./scripts/stop-application.sh
