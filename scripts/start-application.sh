#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

echo "Building graphql-service-app"
./gradlew clean :graphql-service-app:build -PonlyBuild

echo "Application is getting started"
docker-compose build && docker-compose up -d