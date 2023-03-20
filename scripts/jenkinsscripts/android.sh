
if [[ $2 != "0"  &&  $2 != '' &&  $2 != 0 ]];then
    RC="-RC$2"
fi

DATE=`date +%Y-%m-%d_%H-%M`

WORKSPACE=`pwd`/..
DailyBuild=$WORKSPACE/../SandsDailyBuild

VerFile=$WORKSPACE/../sandsbuildversion.txt
if [ ! -e $VerFile ]; then
   touch $VerFile
   echo 1 >> $VerFile
fi
BuildVer=`cat $VerFile`

OUTPUT_DIR="$DailyBuild/android/"
LATEST_DIR="$DailyBuild/android/latest"
PROJECT_DIR=$WORKSPACE/android

echo $WORKSPACE
echo $PROJECT_DIR


function failClear() {
    echo "generate Sandstalk apk failed!"
    rm $APK_DIR/*.apk
}

pushd $PROJECT_DIR
export ST_BUILD_NUMBER="$BuildVer"
export ST_RELEASE_COUNT="$RC"
if [ $1 == 'releaseUAT' ]; then
    APK_DIR=$PROJECT_DIR/app/build/outputs/apk/releaseUAT
    ./gradlew :app:assembleReleaseUAT --quiet
else
    APK_DIR=$PROJECT_DIR/app/build/outputs/apk/release
    ./gradlew :app:assembleRelease --quiet
fi

if [ $? != 0 ];then failClear; exit 1; fi
popd

if [ -e $LATEST_DIR ]; then
    rm -rf $LATEST_DIR
fi
mkdir -p $LATEST_DIR
mkdir -p $OUTPUT_DIR
cp $APK_DIR/*.apk $LATEST_DIR
cp $APK_DIR/*.apk $OUTPUT_DIR

