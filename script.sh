#!/bin/bash

# Usage check
if [ -z "$1" ]; then
  echo "Usage: $0 <number_of_containers> [offset]"
  echo "Example: $0 3 10"
  exit 1
fi

COUNT=$1
OFFSET=${2:-0}   # Default offset = 0

# Loop to start containers
for (( i=1; i<=COUNT; i++ )); do
  num=$((i + OFFSET))
  localPort=$((5700 + num))

  # Run jade in a new terminal (adjust for your terminal type)
  gnome-terminal -- bash -c "java -cp 'lib/jade.jar' jade.Boot \
    -host localhost \
    -port 5698 \
    -local-host localhost \
    -local-port $localPort \
    -name PLATFORMAJADE \
    -container \
    -container-name CONTAINER$num; exec bash"
done
