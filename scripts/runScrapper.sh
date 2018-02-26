#!/bin/bash

cd $(git rev-parse --show-toplevel)
if [[ -e scrapper/target/scrapper-*-dependencies.jar ]]
then
	java -jar scrapper/target/scrapper-*-dependencies.jar
else
	mvn clean install
	java -jar scrapper/target/scrapper-*-dependencies.jar
fi
