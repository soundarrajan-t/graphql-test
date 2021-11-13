#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

. ./scripts/export-environment-variables.sh

process_done=false
until [ $process_done == true ]; do
  echo -e "select which test to run[0] \n 0.all tests \n 1.unit tests \n 2.integration tests \n 3.start application \n 4.start application in debug mode \n 5.cancel run"
  DEFAULT=0
  read -r user_input
  user_input="${user_input:-${DEFAULT}}"

  if [[ -n ${user_input//[0-5]/} ]]; then
    echo "Give a valid input"

  elif [ "$user_input" -eq 0 ]; then
    ./gradlew clean :graphql-service-app:unitTest
    . ./scripts/integration-test.sh
    process_done=true

  elif [ "$user_input" -eq 1 ]; then
    ./gradlew clean :graphql-service-app:unitTest
    process_done=true

  elif [ "$user_input" -eq 2 ]; then
    . ./scripts/integration-test.sh
    process_done=true

  elif [ "$user_input" -eq 3 ]; then
    . ./scripts/start-application.sh
    process_done=true

  elif [ "$user_input" -eq 4 ]; then
    . ./scripts/debug-deploy.sh
    process_done=true

  elif [ "$user_input" -eq 5 ]; then
    exit 0
  fi
done