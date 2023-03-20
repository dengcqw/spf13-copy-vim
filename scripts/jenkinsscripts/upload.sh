DATE=`date +%Y-%m-%d_%H-%M`
DailyBuild=../../SandsDailyBuild
LATEST_IPA_DIR="$DailyBuild/ios/latest_ipa"
LATEST_DIR="$DailyBuild/android/latest"
TestType="$1 RC$5"
Branch=$2
BugIDList=$3

WORKSPACE=`pwd`/..
VerFile=$WORKSPACE/../sandsbuildversion.txt
if [ ! -e $VerFile ]; then
   touch $VerFile
   echo 1 >> $VerFile
fi
BuildVer=`cat $VerFile`

if [ $4 == 'releaseUAT' ]; then
    iOS_CHANNELKEY='38ded0968470a8d945cde72e21a8567a'
    an_CHANNELKEY='1f65ec9ea9d8a6afad1dcf4ab518af81'
else
    iOS_CHANNELKEY='3f654f420eae8f4a4757b285fe8f67eb'
    an_CHANNELKEY='9143162e0162a2d68b00f54f56292b07'
fi

ChangeLog=$(cat <<- EOF
$TestType
BuildNum: $BuildVer Time: $DATE
GitLog:
    $BUILD_URL
$BugIDList
EOF
)

Result=`curl -X POST \
  'https://app-dis.jtexpress.com.cn/api/apps/upload?token=1668fab890db544fa6fe47fdce27be18' \
   --form "channel_key=\"$iOS_CHANNELKEY\"" \
   --form "changelog=\"$ChangeLog\"" \
   --form "branch=\"$Branch\"" \
   --form "file=@\"$LATEST_IPA_DIR/sandytalk.ipa\""`

echo $Result

URL=`node -e "let json = $Result; console.log(json['release_url'])"`
echo $URL
echo $URL >> $DailyBuild/output_ios.txt
echo $URL > $DailyBuild/output_latest.txt

for file in $LATEST_DIR/*.apk; do
    Result=`curl -X POST \
      'https://app-dis.jtexpress.com.cn/api/apps/upload?token=1668fab890db544fa6fe47fdce27be18' \
       --form "channel_key=\"$an_CHANNELKEY\"" \
       --form "changelog=\"$ChangeLog\"" \
       --form "branch=\"$Branch\"" \
       --form "file=@\"$file\""`
    echo $Result
    URL=`node -e "let json = $Result; console.log(json['release_url'])"`
    echo $URL
    echo $URL >> $DailyBuild/output_android.txt
    echo $URL >> $DailyBuild/output_latest.txt
done

