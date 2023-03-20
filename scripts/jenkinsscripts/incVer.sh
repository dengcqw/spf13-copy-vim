WORKSPACE=`pwd`/..
VerFile=$WORKSPACE/../sandsbuildversion.txt

BuildVer=`cat $VerFile`
BuildVer=`expr $BuildVer + 1`
echo $BuildVer > $VerFile
