#!/usr/bin/env bash

if [[ $1 = "run" ]]
then
    echo "Creating database"
    docker exec -it infallible_visvesvaraya /bin/bash /cassandra/database-schema.sh
elif [[ $1 = "truncate" ]]
then
    echo "Truncating database"
    docker exec -it infallible_visvesvaraya bash /cassandra/truncate-database.sh
else
    echo "Cannot know what to do with: $@"
fi