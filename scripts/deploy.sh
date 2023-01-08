#!/bin/bash

chmod +x scripts/*

set -ex

function pause() {
    read -p "$*"
}

PROJECT_NAME=freelec-demo

echo "---------------------------------------------------------"
pause "CI 1. Project Test, Build - Press any key to continue..."
echo "---------------------------------------------------------"

git pull
./gradlew clean build

echo "---------------------------------------------------------"
pause "CI 2. Before Deploy - Press any key to continue..."
echo "---------------------------------------------------------"

mkdir -p before-deploy
cp scripts/*.sh before-deploy/
cp build/libs/*.jar before-deploy/
# before-deploy로 이동후 전체 압축
cd before-deploy && tar cvf before-deploy.tar * && cd ..
# deploy 디렉토리 생성
mkdir  ../deploy
mv before-deploy/before-deploy.tar ../deploy/${PROJECT_NAME}.tar
rm -rf before-deploy

echo "---------------------------------------------------------"
pause 'CD 3. Deploy - Press any key to continue...'
echo "---------------------------------------------------------"

cd ../deploy
tar xvf ${PROJECT_NAME}.tar

echo "---------------------------------------------------------"
pause 'CD 4. Stop - Press any key to continue...'
echo "---------------------------------------------------------"

./stop.sh

echo "---------------------------------------------------------"
pause 'CD 4. Start - Press any key to continue...'
echo "---------------------------------------------------------"

./start.sh

echo "---------------------------------------------------------"
pause 'CD 4. Health Check - Press any key to continue...'
echo "---------------------------------------------------------"

./health.sh
