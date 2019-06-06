#!/bin/bash
./compila_test.sh
export REGISTRY_HOST=localhost
export REGISTRY_PORT=10000
export BLOCKSIZE=5 # el tamaño que considere oportuno
./ejecuta_test.sh
