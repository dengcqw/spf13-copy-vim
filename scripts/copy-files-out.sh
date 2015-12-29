#!/bin/bash
# read relative path from file, then copy files out

filename="$1"
dirPrefix="/Users/pinganhaofang/1_Project/haofangtuo"
fileDir="./filelist.d"

if [ ! -d $fileDir ]
then
    mkdir $fileDir
fi

if [ ! -d $fileDir ]
then
    echo 'create dir fail'
    exit 1
fi

while read -r line
do
    name="$dirPrefix/$line"
    echo $name
    if [ -e "$name" ]
    then
        cp $name $fileDir
    fi
done < "$filename"

echo 'done'

