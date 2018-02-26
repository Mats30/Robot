#!/bin/bash

echo Creating pre-push hook.
set -e
cp pre-push .git/hooks

cd .git/hooks
rm pre-push.sample

chmod 770 pre-push
echo Hook created.
