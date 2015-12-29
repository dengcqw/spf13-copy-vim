#! /bin/bash

logFile=~/Desktop/log.txt
if [ -e $logFile ]
then
    echo $logFile
    rm $logFile
fi

touch $logFile

array=("DengJinlong" "Hui Xu" "jiangshanshan" "maokaiqian" "pinganfang" "李林" "李风磊")
for name in "${array[@]}"; do
    echo $name
    git log --pretty=format:"%an %h %s" --author="$name" --after="2015-11-26" --no-merges --name-only  >> ~/Desktop/log.txt
done

