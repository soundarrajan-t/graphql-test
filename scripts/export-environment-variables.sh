#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

export DB__HOST="$(gopass variables/graphql DB_HOST)"
export DB__PORT="$(gopass variables/graphql DB_PORT)"
export MYSQL__DATABASE="$(gopass variables/graphql MYSQL_DATABASE)"
export MYSQL__ROOT__USERNAME="$(gopass variables/graphql DB_USERNAME)"
export MYSQL__ROOT__PASSWORD="$(gopass variables/graphql MYSQL_ROOT_PASSWORD)"
export OMDB__API="$(gopass variables/graphql OMDB_API)"
export OMDB_API="$(gopass variables/graphql OMDB_API)"
export OMDB__URL="$(gopass variables/graphql OMDB_URL)"
export OMDB_URL="$(gopass variables/graphql OMDB_URL)"
export OMDB__TEST__URL="$(gopass variables/graphql OMDB_TEST_URL)"
export OMDB_TEST_URL="$(gopass variables/graphql OMDB_TEST_URL)"
