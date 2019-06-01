#!/bin/bash
./compila_test.sh
export REGISTRY_HOST=localhost
export REGISTRY_PORT=12388
export BLOCKSIZE=11 # el tamaño que considere oportuno
./ejecuta_test.sh
