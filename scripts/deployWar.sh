#!/bin/bash

cd $(git rev-parse --show-toplevel)/database
mvn clean install
cd /var/lib/tomcat8/webapps 
if [[ -e *.war ]]
then
	rm *.war
fi

cp /home/ubuntu/robot/Robot/database/target/database*.war .

