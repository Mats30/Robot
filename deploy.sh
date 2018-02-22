#!/bin/bash

mvn clean install
mv database/target/database-1.0-SNAPSHOT.war /opt/tomcat/apache-tomcat-9.0.5/webapps/
cd /opt/tomcat/apache-tomcat-9.0.5/
./bin/startup.sh
