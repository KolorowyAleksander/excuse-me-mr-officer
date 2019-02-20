#!/usr/bin/env bash

if [[ $1 = "run" ]]
then
    echo "Creating database"
    docker exec -it database_cassandra-one_1 /bin/bash /cassandra/database-schema.sh
elif [[ $1 = "truncate" ]]
then
    echo "Truncating database"
    docker exec -it database_cassandra-one_1 bash /cassandra/truncate-database.sh
else
    echo "Cannot know what to do with: $@"
fi