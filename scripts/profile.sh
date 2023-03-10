#!/usr/bin/env bash

# bash는 return value가 안되니 *제일 마지막줄에 echo로 해서 결과 출력*후, 클라이언트에서 값을 사용한다
# 쉬고 있는 profile 찾기: real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음
function find_idle_profile()
{

    # 현재 엔진엑스가 바라보고 있는 스프링 부트가 정상적으로 수행 중인지 확인한다.
    # 응답값을 HttpStatus로 받는다.
    # -s 옵션: 정숙 모드. 진행 내역이나 메시지등을 출력하지 않는다.
    # -o 옵션으로 remote data 도 /dev/null 로 보내면 결과물도 출력되지 않는다.
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        # 오류가 발생했다면 400~503 사이로 발생하니 400 이상은 모두 에러로 보고
        # 현재 nginx와 연결된 스프링 부트가 아무것도 없음으로 그냥 real2로 지정하여
        # IDLE을 real1이 되도록 하여 real1을 먼저 사용하도록 함
        CURRENT_PROFILE=real2
    else
        # 장상이면 200
        # 둘 중 반드시 하나는 엔진엑스와 연결 되어 있음
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    # IDLE_PROFILE
    # 엔진엑스와 연결되지 않은 profile이다.
    # 스프링 부트 프로젝트를 이 연결되지 않은 profile로 연결하기 위해 출력한다.
    if [ ${CURRENT_PROFILE} == real1 ]
    then
        IDLE_PROFILE=real2
    else
        IDLE_PROFILE=real1
    fi

    # bash라는 스크립트는 값을 반환하는 기능이 없다.
    # 그래서 제일 마지막 줄에 echo로 결과를 출력하여, $(find_idle_profile) 호출하여 이 echo 출력 값을 잡아서 사용한다.
    # 중간에 echo를 사용해서는 안된다.
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
        echo "8081"
    else
        echo "8082"
    fi
}