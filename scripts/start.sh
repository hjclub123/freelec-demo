#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ubuntu/apps/freelec-step3
PROJECT_NAME=freelec-demo

echo "> Build 파일 복사"
echo "> $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
# tail -n 으로 가장 나중의 jar 파일(최신 파일)을 찾는다.
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

#--------------------------------------------------------------
echo "> $JAR_NAME 실행"
IDLE_PROFILE=$(find_idle_profile)

# real1 or real2
# IDLE_PROFILE을 통해 properties 파일을 가져오고 active profile을 지정한다.
echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
#nohup java -jar \
#    -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ubuntu/apps/freelec-step3/application-oauth.properties,/home/ubuntu/apps/freelec-step3/application-real-db.properties \
#    -Dspring.profiles.active=$IDLE_PROFILE \
#    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ubuntu/apps/freelec-step3/application-oauth.properties,/home/ubuntu/apps/freelec-step3/application-real-db.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME
