#!/bin/bash

echo 'building members'
cd members
./gradlew clean build

echo 'building vhs-catalog'
cd ../vhs-catalog
./gradlew clean build fatJar

echo ''
echo 'done'
echo ''
