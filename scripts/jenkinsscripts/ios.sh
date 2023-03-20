#
# jenkins daily build shell script
#
# This script must be run in iOS project root dir
# Will create a folder DailyBuild/ with timestamp which contains archive zip and ipa
# And a folder latest_ipa/ stores newly created ipa
#
# Issues:
# 1. "User interaction is not allowed. Command /usr/bin/codesign failed with exit code 1"
#   http://stackoverflow.com/a/20208104
#   run the following command right before attempting to build: security -v unlock-keychain -p "$KEYCHAIN_PASSWORD" login.keychain
# 2. "xcodebuild: error: The workspace named "Sandstalk" does not contain a scheme named "Sandstalk""
#   login GUI, open workspace with Xcode, select Sandstalk scheme
#   Because Different user has different scheme config file
#


if [[ $2 != "0"  &&  $2 != '' &&  $2 != 0 ]];then
    RC="-RC$2"
fi

echo $RC

WORKSPACE=`pwd`/..
DailyBuild=$WORKSPACE/../SandsDailyBuild
VerFile=$WORKSPACE/../sandsbuildversion.txt
if [ ! -e $VerFile ]; then
   touch $VerFile
   echo 1 >> $VerFile
fi
BuildVer=`cat $VerFile`
PRJ_DIR=$WORKSPACE/ios

echo $PRJ_DIR
echo $WORKSPACE

pushd $PRJ_DIR

# check if Sandstalk project
SANDYTALK=`xcodebuild -list | grep 'sandytalk' | wc -l`
if [ $SANDYTALK == 0 ]; then
    echo 'Current Dir is not sandytalk project, Run this script in iOS project root dir'
    exit 1
fi

# Project Info which need config by user
XCODE_WORKSPACE="$PRJ_DIR/sandytalk.xcworkspace"
XCODE_INFO_PLIST="$PRJ_DIR/sandytalk/Info.plist"
if [ $1 == 'releaseUAT' ]; then
    EXPORT_PLIST_FILE="$WORKSPACE/scripts/ExportOptions_uat.plist"
    CONFIG="ReleaseUAT"
else
    EXPORT_PLIST_FILE="$WORKSPACE/scripts/ExportOptions.plist"
    CONFIG='Release'
fi

XCODE_SCHEME=sandytalk

APP_VERSION=$BuildVer
DATE=`date +%Y-%m-%d_%H-%M-%S`
# prepare output dir, don't change below config
if [ ! -e $DailyBuild/ios/latest_ipa ]; then
    mkdir -p $DailyBuild/ios/latest_ipa
fi
OUTPUT_DIR="$DailyBuild/ios/${DATE}_${XCODE_SCHEME}_BuildNum${APP_VERSION}"
LATEST_IPA_DIR="$DailyBuild/ios/latest_ipa"
ARCHIVE_DIR="$OUTPUT_DIR/archive"
EXPORT_DIR="$OUTPUT_DIR"


if [ ! -e $OUTPUT_DIR ]; then
	mkdir $OUTPUT_DIR
fi
if [ ! -e $ARCHIVE_DIR ]; then
	mkdir $ARCHIVE_DIR
fi
if [ ! -e $EXPORT_PLIST_FILE ]; then
	echo "export options plist can't find"
fi

if [ -e $LATEST_IPA_DIR ]; then
    echo "latest ipa dir exist. rm -rf $LATEST_IPA_DIR"
    rm -rf $LATEST_IPA_DIR
fi

# update submodule
git submodule init
git submodule update

#cd ..
#npm install --force
#cd -
pod install

ARCHIVE_FILE="${XCODE_SCHEME}.xcarchive"
ARCHIVE_TMP_PATH="$ARCHIVE_DIR/$ARCHIVE_FILE"

function clearBuild() {
    if [ -e ./build ]; then rm -rf ./build; fi
    if [ -e $ARCHIVE_DIR ]; then rm -rf $ARCHIVE_DIR; fi
}

function failClear() {
    echo "Archive Sandstalk failed!"
    clearBuild
    if [ -e $OUTPUT_DIR ]; then rm -rf $OUTPUT_DIR; fi
}

# unlock kenchain
security -v unlock-keychain -p "mobile@team" login.keychain


# build & export
xcodebuild archive -workspace ${XCODE_WORKSPACE} \
                   -scheme ${XCODE_SCHEME} \
                   -configuration ${CONFIG}  \
                   -archivePath ${ARCHIVE_TMP_PATH} \
                   -destination 'generic/platform=iOS' \
                   ENABLE_BITCODE=NO \
                   CURRENT_PROJECT_VERSION="$BuildVer" Custom_Release_Count="$RC" -quiet

if [ $? != 0 ];then failClear; exit 1; fi


xcodebuild -exportArchive \
	   -archivePath $ARCHIVE_TMP_PATH \
	   -exportPath $EXPORT_DIR \
    -exportOptionsPlist $EXPORT_PLIST_FILE \
    CODE_SIGN_IDENTITY="Apple Development: 晓珍 韦 (5488D8L89H)" -quiet

if [ $? != 0 ];then failClear; exit 1; fi

# zip symbol files
#if [ -e $ARCHIVE_TMP_PATH ]; then
    #echo 'will zip archive for download'
    #echo 'pushd'
    #pushd $ARCHIVE_DIR
    #zip -r -9 -q $ARCHIVE_TMP_PATH.zip ./$ARCHIVE_FILE
    #cp $ARCHIVE_FILE.zip ../
    #echo 'popd'
    #popd
#fi

clearBuild

mkdir $LATEST_IPA_DIR
cp $EXPORT_DIR/* $LATEST_IPA_DIR
echo "Archive Sandstalk Done."
echo "Latest ipa location: $LATEST_IPA_DIR/$XCODE_SCHEME.ipa"

popd
