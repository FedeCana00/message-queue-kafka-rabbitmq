#!/bin/bash

mvn clean package -Dmaven.test.skip=true

docker compose build

docker compose up -d