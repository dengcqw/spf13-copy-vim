#! /bin/bash
# read relative path from a file, then copy from old dir to new dir

filename="$1"
newdirPrefix="/Users/pinganhaofang/1_Project/haofangtuo/haofang"
dirPrefix="/Users/pinganhaofang/1_Project/haofangtuo/haofang_master"

while read -r line
do
    name="$dirPrefix/$line"
    toFile="$newdirPrefix/$line"
    echo $name
    if [ -e "$name" ]
    then
        cp $name $toFile
    fi
done < "$filename"
