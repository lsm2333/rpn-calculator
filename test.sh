#!/usr/bin/env bash

# 使用maven命令去进行单元测试

mvn clean package -Dmaven.test.skip=true

mvn test

