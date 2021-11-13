#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

until [ $(curl -s -o /dev/null -w "%{http_code}" http://localhost/actuator/health) == 200 ]; do
  continue
done