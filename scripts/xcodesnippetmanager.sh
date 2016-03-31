#        __         __
#   ____/ /__  ____/ /___ ___  ____  ____  _____
#  / __  / _ \/ __  / __ `__ \/ __ \/ __ \/ ___/
# / /_/ /  __/ /_/ / / / / / / /_/ / / / /__  /
# \__,_/\___/\__,_/_/ /_/ /_/\____/_/ /_/____/
#
# This script is used to manage Xcode code snippets. 
# Inspired by https://github.com/lukeredpath/xcodesnippets but
# I wanted to create a pure bash alternitave for fun.
# 
# It allows you to: 
#   - Migrate: 
#       Copy user snippets to shared folder with human readable names
#   - Import:
#       Import .codesnippet file to the managed folder and link into 
#       Xcodes folder as well
#   - Exprot:
#       Exports all your user snippets in Xcodes folder to ~/XCSnippets
#       or a folder you specify. Names files with human readble title.
#   - Link:
#       Links all the .codesnippet files in managed folder to Xcode folder.
#       Usefull if you copy new snippet in without using import tool.
#   - List:
#       Lists all the snippets in the managed folder and Xcode folder by
#       title and path.

SNIPPET_FOLDER=~/Library/Developer/Xcode/UserData/CodeSnippets/
MANAGED_FOLDER=~/.managedXCSnippets/


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

function checkDir(){
  if [ ! -d "$1" ]
  then
    echo "Do you want to make export folder: $1"
    echo "[Y/n]: \c"

    read cont

    if [ -z "$cont" ]
    then
      cont="y"
    elif [[ "$cont" == "Y" ]]
    then
      cont="y"
    elif [[ "$cont" == "N" ]]
    then
      cont="n"
    fi 
 
    if [[ "$cont" == "y" ]]
    then
      echo "Making folder..."
      mkdir $1
    else
      echo "Exiting"
      exit 0
    fi
  fi
}

function listManaged(){
  if [ -r $MANAGED_FOLDER ]
  then
    find $MANAGED_FOLDER -name "*.codesnippet" | while read file 
    do
      getSnipTitle "$file" title
      file=`basename "$file"`
      echo "$title => $MANAGED_FOLDER$file"
    done
  else
    echo "Managed folder is not readable"
  fi
}

function listSnippets(){
  if [ -r $SNIPPET_FOLDER ]
  then
    for file in `ls $SNIPPET_FOLDER`
    do
      getSnipTitle "$SNIPPET_FOLDER$file" title
      echo "$title => $file"
    done
  else
    echo "Snippets folder is not readable"
  fi
}

function exportSnippets(){
  new_path=$1

  if [[ ${new_path:(-1)} != "/" ]]
  then
    new_path="$new_path/"
  fi

  if [[ ${new_path:0:1} != "/" ]]
  then
    new_path="`pwd`/$new_path"
  fi

  checkDir "$new_path"

  echo "Exporting Snippets to $new_path"
  echo ""

  for file in `ls $SNIPPET_FOLDER`
  do
    oldpath="$SNIPPET_FOLDER$file"
    
    getSnipTitle "$oldpath" title

    newpath="$new_path$title.codesnippet"

    echo "$oldpath => $newpath"
    cp "$oldpath" "$newpath"
  done

}

function migrate(){
  echo "Migraging Snippets"
  echo ""

  checkDir "$MANAGED_FOLDER"

  for file in `ls $SNIPPET_FOLDER`
  do    
    oldpath="$SNIPPET_FOLDER$file"

    getSnipTitle "$oldpath" title

    newpath="$MANAGED_FOLDER$title.codesnippet"

    echo "$file => $title.codesnippet"

    cp "$file" "$newpath"
  done
}

function import(){
  checkDir "$MANAGED_FOLDER"
  checkDir "SNIPPET_FOLDER"

  new_file=`basename "$1"`
  new_file_path="$1"

  if [[ ${new_file_path:0:1} != "/" ]]
  then
    new_file_path="`pwd`/$new_file_path"
  fi

  getSnipID "$new_file_path" snip_guid

  echo "Importing $1"
  echo "GUID: $snip_guid"
  
  cp "$new_file_path" "$MANAGED_FOLDER$new_file" 
  cp "$new_file_path" "$SNIPPET_FOLDER$snip_guid.codesnippet"
}

function linkSnippets(){
  echo "Linking Snippets"
  echo ""
  checkDir "$MANAGED_FOLDER"
  checkDir "$SNIPPET_FOLDER"

  find $MANAGED_FOLDER -name "*.codesnippet" | while read file 
  do    
    file=`basename "$file"`
    oldpath="$MANAGED_FOLDER$file"

    getSnipID "$oldpath" snippet_guid

    newpath="$SNIPPET_FOLDER$snippet_guid.codesnippet"

    echo "$oldpath => $newpath"

    cp "$oldpath" "$newpath"
  done
}

#############################
#############################
###
###  Start of Script
###
#############################
#############################

echo "xcodesnippets manage"

## Reading command line args
OPTIND=1 #reset for safety

# a b c d e f g h i j k l m n o p q r s t u v w x y z

while getopts ":e:hi:lmp" flag
do
  case "$flag" in
    e) # export existing snippets to to output location or ~/XCSnippets
      exportSnippets "$OPTARG"
      exit 0
      ;;
    h) # show help
      ;;
    i) # import snippet file or bundle
      import "$OPTARG"
      exit 0
      ;;
    l) # Link managed snippets folder to xcode snippets folder
      linkSnippets
      exit 0
      ;;
    m) # Migrage snipptes from xcode to managed folder
      migrate
      exit 0
      ;;
    p) # Prints out current snippets in managed and xcode folder
      echo "Managed Snippets:"
      listManaged
      echo ""
      echo "Xcode Snippets:"
      listSnippets
      exit 0
      ;;
    :)
      if [[ "$OPTARG" -eq "e" ]]; then
        exportSnippets ~/XCSnippets
      else
        echo "Error: -$OPTARG needs a value"
        exit 1
      fi
      ;;
    \?)
      echo "Error: -$OPTARG is and invalad flag"
      exit 1
      ;;
  esac
done
