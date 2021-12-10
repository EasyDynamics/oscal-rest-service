#!/bin/bash

for file in $1/*;
do
  if [[ ${file: -5} = ".json" ]];
  then
    contents=$(cat $file | jq --raw-output '.');
    uuid=$(cat $file | jq --raw-output '."system-security-plan"' | jq --raw-output '."uuid"');
    if [ $uuid = $2 ];
    then
      echo $contents;
      exit 0;
    fi
  fi
done