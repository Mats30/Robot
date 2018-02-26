#!/bin/bash

cd $(git rev-parse --show-toplevel)/scripts
./deployAngular.sh
./deployWar.sh
./deployScrapper.sh
