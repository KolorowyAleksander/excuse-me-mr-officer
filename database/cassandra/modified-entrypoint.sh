#/usr/bin/env bash

echo "Modyfing default route to ${ROUTER_ADDRESS}"
ip r del default
ip r add default via ${ROUTER_ADDRESS}

# run the default entrypoint
echo "Running cassandra entrypoint $@"
source docker-entrypoint.sh "$@"