#!/usr/bin/env bash

set -euo pipefail

SPRING_PROFILES_ACTIVE=standardDatabase,dev \
  mvn clean spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx128m"
