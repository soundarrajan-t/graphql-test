#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

echo "Building graphql-service-app"
./gradlew clean :graphql-service-app:build -PonlyBuild

echo "Deploy DB Container"
docker-compose -f debug.docker-compose.yml build
docker-compose -f debug.docker-compose.yml up -d
