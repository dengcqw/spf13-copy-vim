#!/usr/bin/env bash

# toggle Dock icon
function toggleDockIcon () {
    pb='/usr/libexec/PlistBuddy'
    AppInfo="/Applications/$App/Contents/Info.plist"
    #echo $AppInfo

    echo "Do you wish to hide $App in Dock? input a number"
    select ync in 'Hide' 'Show' 'Cancel'; do
        case $ync in
            'Hide' )
                $pb -c "Add :LSUIElement bool true" "$AppInfo"
                echo "Hide"
                echo "relaunch App to take effectives"
                break
                ;;
            'Show' )
                $pb -c "Delete :LSUIElement" "$AppInfo"
                echo "Show"
                echo "run killall "$App" to exit, and then relaunch it"
                break
                ;;
            'Cancel' )
                echo "Cancel"
                return 0
                ;;
        esac
    done
}

if [ -z "$1" ];then
    echo "Usage:  toggleDockIcon [app name]"
    exit 0
fi

APP_LIST=($(find /Applications -maxdepth 1  -iname "*$1*" | sed 's/ /-/g'))

# print app list
echo "Find apps:"
for line in "${APP_LIST[@]}";
do
    echo "    $line" | sed 's/-/ /g'
done

# toggle dock icon for each app
for line in "${APP_LIST[@]}";
do
    App=`echo ${line##*/} | sed 's/-/\\ /g'`
    echo "" #new line
    toggleDockIcon
done

