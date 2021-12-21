#!/bin/bash
if [ "${1:0:11}" = "-oscalfile:" -a ${1: -5} = ".json" ]; then
	file=${1:11};
else
	echo "You must specify -oscalfile:\$file, where \$file is a json oscal file.";
	exit 1;
fi

current_directory="$(pwd)"
cd $(dirname $file);
file=$(basename $file);
contents="$(jq '.' $file | jq .)"

#Will contain the replaced uuids to avoid replacing uuid multiple times
uuid_arr=();

#Will contain the auto-generated uuids
auto_generated_uuids=();

while read -r line; do
	#Extracting the uuid from the line
  old_uuid="$(echo $line | sed 's/^[ \t]*\"uuid\": //' | sed 's/[\" | ,]//g')";
	old_uuid=${old_uuid::-1};

	#Checking if we already replaced the uuid
	array_contains=$(echo ${uuid_arr[@]} | grep -o "$old_uuid" | wc -w);

	if [ $array_contains = 0 ]; then
		#adding the uuid (in the current line) to the array of replaced uuids
		uuid_arr+=($old_uuid);

		#auto generating a new uuid with the uuidgen command
		auto_generated_uuids+=("$(uuidgen)");
	fi
done < <(grep \"uuid\" <<< $contents); #We only want to read lines with uuids

for i in "${!uuid_arr[@]}"; do
	sed -i "s/"${uuid_arr[i]}"/"${auto_generated_uuids[i]}"/g" "$file";
done;

cd "$current_directory"