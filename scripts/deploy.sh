#!/bin/bash
set -e

cd ../
mvn clean install;
sudo cp -R database/target/database-1.0-SNAPSHOT.war /var/lib/tomcat7/webapps
