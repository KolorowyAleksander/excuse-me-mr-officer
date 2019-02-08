#/usr/bin/env bash

echo "Deleting default route on the router"
ip r del default

echo "Router CMD is: $@, executing it"
exec "$@"