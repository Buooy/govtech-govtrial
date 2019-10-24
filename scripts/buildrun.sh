#!/bin/sh
./gradlew clean build &
java -jar build/libs/govtrial-0.1.0.jar &