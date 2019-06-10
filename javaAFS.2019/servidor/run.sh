#!/bin/bash
fuser -k 10000/tcp
rm AFSDir/*
echo "En un lugar de la mancha que no me quiero acordar" >> ./AFSDir/test1
echo "Esto es una simple prueba" >> ./AFSDir/test2 
./compila_servidor.sh
./arranca_rmiregistry 10000 &
./ejecuta_servidor.sh 10000
