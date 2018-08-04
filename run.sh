#!/bin/bash

docker network create -d bridge vhs

docker run \
    --rm --name=jaeger \
    -p 5775:5775/udp \
    -p 16686:16686 \
    --network=vhs \
    jaegertracing/all-in-one:latest &

docker run \
    --rm --name=members \
    -p 8080:8080 \
    --network=vhs \
    -e MOVIESERVICE_DOMAIN=vhscatalog \
    -e MOVIESERVICE_PORT=4567 \
    -e OPENTRACING_JAEGER_LOG_SPANS=true \
    -e OPENTRACING_JAEGER_UDP-SENDER_HOST=jaeger \
    -e OPENTRACING_JAEGER_UDP-SENDER_PORT=6831 \
    members &

    # -e OPENTRACING_JAEGER_HTTP_SENDER_URL=http://jaeger:16686/api/traces \

docker run \
    --rm --name=vhscatalog \
    -p 8081:4567 \
    --network=vhs \
    -e jaeger.host=jaeger \
    vhs-catalog &

# jaeger ui http://localhost:16686/

# test curl localhost:8080/members/2/
