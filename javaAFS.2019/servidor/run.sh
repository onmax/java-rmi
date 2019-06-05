#!/bin/bash
fuser -k 10000/tcp

./compila_servidor.sh
./arranca_rmiregistry 10000 &
./ejecuta_servidor.sh 10000
