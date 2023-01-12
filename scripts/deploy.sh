#!/bin/bash

set -ex

function pause() {
    read -p "$*"
}

PROJECT_NAME=freelec-demo

set +x
echo "---------------------------------------------------------"
pause "CI 1. Project Test, Build - Press any key to continue..."
echo "---------------------------------------------------------"
set -x

git pull
#./gradlew clean build -x test
./gradlew build -x test

set +x
echo "---------------------------------------------------------"
pause "CI 2. Before Deploy - Press any key to continue..."
echo "---------------------------------------------------------"
set -x

mkdir -p before-deploy
cp scripts/*.sh before-deploy/
cp build/libs/*.jar before-deploy/
# before-deploy로 이동후 전체 압축
cd before-deploy && tar cvf before-deploy.tar * && cd ..

rm -rf ../deploy
mkdir ../deploy
mv before-deploy/before-deploy.tar ../deploy/${PROJECT_NAME}.tar
rm -rf before-deploy

set +x
echo "---------------------------------------------------------"
pause 'CD 3. Deploy - Press any key to continue...'
echo "---------------------------------------------------------"
set -x

cd ../deploy/
tar xvf ${PROJECT_NAME}.tar
rm -f ${PROJECT_NAME}.tar

set +x
echo "---------------------------------------------------------"
pause 'CD 4. Stop - Press any key to continue...'
echo "---------------------------------------------------------"
set -x

./stop.sh

set +x
echo "---------------------------------------------------------"
pause 'CD 5. Start - Press any key to continue...'
echo "---------------------------------------------------------"
set -x

./start.sh

set +x
echo "---------------------------------------------------------"
pause 'CD 6. Health Check - Press any key to continue...'
echo "---------------------------------------------------------"
set -x

./health.sh

