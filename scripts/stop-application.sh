#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

echo "Stopping the Application"
docker-compose down