#!/bin/bash
set -e

cd ../
mvn clean install;
sudo cp -R database/target/database*.war /var/lib/tomcat7/webapps
