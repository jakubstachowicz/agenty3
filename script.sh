#!/bin/bash

# Usage: ./run_jade.sh <number_of_containers> [offset]
# Example: ./run_jade.sh 3 10

if [ -z "$1" ]; then
  echo "Usage: $0 <number_of_containers> [offset]"
  echo "Example: $0 3 10"
  exit 1
fi

COUNT=$1
OFFSET=${2:-0}   # Default offset = 0

for (( i=1; i<=COUNT; i++ )); do
  num=$((i + OFFSET))
  localPort=$((5700 + num))

  # Launch in a new terminal; close automatically when process ends
  gnome-terminal -- bash -c "
    echo 'Starting CONTAINER${num} on port ${localPort}...';
    java -cp 'lib/jade.jar' jade.Boot \
      -host localhost \
      -port 5698 \
      -local-host localhost \
      -local-port ${localPort} \
      -name PLATFORMAJADE \
      -container \
      -container-name CONTAINER${num};
  "
done
