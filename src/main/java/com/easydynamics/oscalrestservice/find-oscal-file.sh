#!/bin/bash

for file in $1/*;
do
  if [[ ${file: -5} = ".json" ]];
  then
    echo $oscaltype
    contents=$(cat $file | jq --raw-output '.')
    uuid=$(cat $file | jq --raw-output '.[]' | jq --raw-output '."uuid"')
    if [ $uuid = $2 ];
    then
      echo $contents
    fi
  fi
done