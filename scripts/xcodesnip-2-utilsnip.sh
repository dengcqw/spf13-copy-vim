#!/usr/bin/env bash

get_snip_title_tmp=

function getSnipTitle(){
  file="$1"
  get_snip_title_tmp="-99"
  if [ -r "$file" ]
  then
    get_snip_title_tmp=`cat "$file" | grep -A 1 -e "IDECodeSnippetTitle" | grep "<string>" | cut -d ">" -f 2 | cut -d "<" -f 1`
    if [ "$get_snip_title_tmp" == "" ]; then
        get_snip_title_tmp="-99"
    fi
    return 0
  else
    return 1
  fi
}

get_snip_trigger_tmp=

function getSnipTrigger() {
  file="$1"
  get_snip_trigger_tmp="-99"
  if [ -r "$file" ]
  then
    get_snip_trigger_tmp=`cat "$file" | grep -A 1 -e "IDECodeSnippetCompletionPrefix" | grep "<string>" | cut -d ">" -f 2 | cut -d "<" -f 1`
    if [ "$get_snip_trigger_tmp" == "" ]; then
        get_snip_trigger_tmp="-99"
    fi
    return 0
  else
    return 1
  fi
}

get_snip_content_tmp=

function getSnipContent() {
  file="$1"
  get_snip_content_tmp="-99"
  if [ -r "$file" ]
  then
    get_snip_content_tmp=`sed -n "/IDECodeSnippetContents/,/IDECodeSnippetIdentifier/p" $file  | grep -A 100 -e "<string>" | grep -B 100 -e "</string>"`
    if [ "$get_snip_content_tmp" == "" ]; then
        get_snip_content_tmp="-99"
    fi
    return 0
  else
    return 1
  fi
}

output=objc_xcode.snippets

folder=$1

if [ -d "$folder" ]; then
    echo "cd folder"
    cd $folder
else
    echo "not a folder"
    exit 1
fi

if [ -e "$output" ]; then
    echo "rm $output"
    rm $output
fi

echo "create $output"
touch $output

count = 0

for file in *
do
    if [[ "${file##*.}" == 'codesnippet' ]]; then
        echo "converting file: [$file]"
        (( count = count +1 ))

        getSnipTitle "$file"
        getSnipTrigger "$file"
        getSnipContent "$file"

        echo "snippet $get_snip_trigger_tmp  \"$get_snip_title_tmp\"" >> $output
        echo "$get_snip_content_tmp" >> $output
        echo "endsnippet" >> $output
        echo "" >> $output
    fi 
done

echo "total: $count"

cd -



# remove <string> </string>
# sed is hard to insert new line
# use vim instand
#sed -i -e "s/<string>/<string>`echo ""`/g" objc_xcode.snippets
#sed -i -e "s/<\/string>/`echo`/g" objc_xcode.snippets
#sed -i -e "/<string>, <\/string>/d" objc_xcode.snippets

# :1,$s/<string>/<string>\r/g
# :1,$s/<\/string>/\r<\/string>/g
# g/<string>/d
# g/<\/string>/d
# %s/&lt;#/${1:/g
# %s/#&gt;/}/g
