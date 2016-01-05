# /bin/zsh

# read argvs
# http://stackoverflow.com/a/14203146

if [[ $# < 1 ]];
then
echo "usage: clearHistory [word1] [word2]\n";
exit 0;
fi

GREP_PATTEN=""

while [[ $# > 0 ]]
do
    if [ "$GREP_PATTEN" == "" ];then
        GREP_PATTEN=$1
    else
        GREP_PATTEN+="\|$1"
    fi
    shift
done

echo $GREP_PATTEN

ZSH_HISTORY=$HOME/.zsh_history
grep -v "$GREP_PATTEN" $ZSH_HISTORY  > $HOME/zsh_his.temp;
mv $HOME/zsh_his.temp $ZSH_HISTORY;

