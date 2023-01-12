#!/bin/bash

REPOSITORY=/home/ubuntu/apps/freelec-step3
PROJECT_NAME=freelec-demo-src

cd $REPOSITORY/$PROJECT_NAME

echo "> 프로젝트 Build"
./gradlew clean build

echo "> repository root 디렉토리로 이동"
cd $REPOSITORY

echo "> Build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없음"
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID

    echo "sleep 5"
    sleep 5
fi

echo "> 새 어플리케이션 배포"
# tail -n 으로 가장 나중의 jar 파일(최신 파일)을 찾는다.
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 실행"
#-------------------------------------------
# H2 사용
#-------------------------------------------
#java -jar \
#    -Dspring.config.location=classpath:/application.properties,$REPOSITORY/application-oauth.properties \
#     $JAR_NAME

#-------------------------------------------
# RDS 사용
#-------------------------------------------
# real 한개의 애플리케이션만 사용: 8080
#nohup java -jar \
#    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,$REPOSITORY/application-oauth.properties,$REPOSITORY/application-real-db.properties \
#    -Dspring.profiles.active=real \
#    $JAR_NAME > nohup.out 2>&1 &

java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,$REPOSITORY/application-oauth.properties,$REPOSITORY/application-real-db.properties \
    -Dspring.profiles.active=real \
     $JAR_NAME
