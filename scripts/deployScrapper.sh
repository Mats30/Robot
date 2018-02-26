#!/bin/bash

cd $(git rev-parse --show-toplevel)/scrapper
mvn clean install
