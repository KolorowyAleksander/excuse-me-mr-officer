#!/usr/bin/env bash

if [[ $1 = "up" ]]
then
    echo "Connecting partition nodes"
    docker exec -it database_router_1 /bin/bash -c "ip l set eth0 $1"
elif [[ $1 = "down" ]]
then
    echo "Disconnecting nodes - partition"
    docker exec -it database_router_1 /bin/bash -c "ip l set eth0 $1"
else
    echo "\$1 must be 'up' or 'down', but is: $1"
fi