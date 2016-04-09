#!/usr/bin/env bash

function getSnipTitle(){
  file="$1"
  get_snip_title_tmp="-99"
  if [ -r "$file" ]
  then
    get_snip_title_tmp=`cat "$file" | grep -A 1 -e "IDECodeSnippetTitle" | grep "<string>" | cut -d ">" -f 2 | cut -d "<" -f 1`
    eval "$2='$get_snip_title_tmp'"
    return 0
  else
    return 1
  fi
}

function getSnipID(){
  file="$1"
  if [ -r "$file" ]
  then
    get_snip_guid_tmp=`cat "$file" | grep -A 1 -e "IDECodeSnippetIdentifier" | grep "<string>" | cut -d ">" -f 2 | cut -d "<" -f 1`
    eval "$2='$get_snip_guid_tmp'"
    return 0
  else
    return 1
  fi
}

cd $1

for file in *
do
  echo $file
  getSnipID "$file" snip_title
  echo $snip_title
  mv $file  $snip_title.codesnippet
done


