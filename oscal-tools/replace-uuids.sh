#!/bin/bash
if [ "${1: -5}" = ".json" ]; then
  file="$1"
else
  echo "You must specify a \$FILE argument, where \$FILE is a JSON OSCAL file."
  exit 1
fi

#We only want to update lines containing uuid(s)
uuids="$(sed 's/[ \t]//g' "$file" | tr ',' '\n' | grep \"uuid\")"

#Will contain the replaced uuids to avoid replacing uuid multiple times
uuid_array=()

#Will contain the auto-generated uuids
auto_generated_uuids=()

while read -r line; do
  uuid="$(sed 's/^.*"uuid":"\(.*\)"/\1/g' <<< "$line")" #Extracting the uuid

  #Checking if we have already found the current value of uuid in the file
  if ! echo "${uuid_array[@]}" | grep --quiet --only-matching "$uuid"; then	
    #adding the uuid (in the current line) to the array of replaced uuids
    uuid_array+=("$uuid")

    #auto generating a new uuid with the uuidgen command
    auto_generated_uuids+=("$(uuidgen)")
  fi
done < <(echo "$uuids")

for i in "${!uuid_array[@]}"; do
  sed -i "s/${uuid_array[i]}/${auto_generated_uuids[i]}/g" "$file"
done
