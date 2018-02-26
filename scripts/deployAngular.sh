#!/bin/bash

cd $(git rev-parse --show-toplevel)/robot
npm install
ng build --base-href=/angular/
rm -r /var/lib/tomcat8/webapps/angular/*
cp -r $(git rev-parse --show-toplevel)/robot/dist/* /var/lib/tomcat8/webapps/angular
