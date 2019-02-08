#!/usr/bin/env bash

if [[ $1 = "run" ]]
then
    docker exec -it database_cassandra-one_1 "bash /cassandra/database-schema.sh"
elif [[ $1 = "truncate" ]]
then
    docker exec -it database_cassanra-one_1 "bash /cassandra/truncate-database.sh"
else
    echo "Cannot know what to do with: $@"
fi