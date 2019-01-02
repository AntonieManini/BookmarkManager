#!/bin/bash
mvn clean install
java -jar webapp-runner.jar ./target/bookmark-manager.war
