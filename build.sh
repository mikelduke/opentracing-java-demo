#!/bin/bash

echo 'building members'
cd members
./gradlew clean build
cd ..

echo 'building vhs-catalog'
cd vhs-catalog
./gradlew clean build fatJar
cd ..

echo ''
echo 'docker'
echo 'building members'
cd members
docker build -t members .
cd ..

echo 'building vhs-catalog'
cd vhs-catalog
docker build -t vhs-catalog .

echo ''
echo 'done'
echo ''
