#!/bin/bash
./compila_servidor.sh
./arranca_rmiregistry 12388 &
./ejecuta_servidor.sh 12388
