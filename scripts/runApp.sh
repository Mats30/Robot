#!/bin/bash

cd $(git rev-parse --show-toplevel)/scripts
./deployApp.sh
./runScrapper.sh
