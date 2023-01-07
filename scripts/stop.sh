#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
# 현재 stop.sh가 속해 있는 경로를 찾는다.
ABSDIR=$(dirname $ABSPATH)
# import 구문으로 profile.sh의 function을 사용할 수 있다.
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
# 해당 포트를 사용중인 PID 확인
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
    echo "> 현재 구동중인 애플리케이션 없음"
else
    echo "> kill -15 $IDLE_PID"
    kill -15 ${IDLE_PID}
    sleep 5
fi
