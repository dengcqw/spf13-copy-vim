# sh listAllPng.sh | sed 's/\(.*\)@.*/\1/g' | sed 's/\(.*\)\.png/\1/g' | sort | uniq  > TVGuor/Controllers/Settings/DebugTools/InAppPngList.txt

function listFile() {
    find ./ -regex ".*\.png" | sed "s/.*Pods.*//g" | sed "s/.*UnicomSDK.*//g" | sed "s/.*today-.*//g" | sed "s/.*tw\_.*//g"
}

#imageset，再去读图片名
listFile | grep imageset | sed "s/.*\/\(.*\)\.imageset.*/\1/g"
#除去imageset，再去读图片名
listFile | sed "s/.*\/\(.*\)\.imageset.*//g" | sed "s/.*\/\(.*\.png\)/\1/g"

# 增加引号
#while read line; do echo '"'$line'",' >> quoteList.txt; done < allpng_name.txt

