#!/usr/bin/env bash

echo "Modyfing default route to ${ROUTER_ADDRESS}"
ip r del default
ip r add default via ${ROUTER_ADDRESS}

echo "Running cassandra entrypoint with CMD: $@"
/usr/local/bin/docker-entrypoint.sh "$@"