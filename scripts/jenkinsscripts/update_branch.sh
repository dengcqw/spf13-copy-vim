# git clone ssh://git@code.jms.com:2222/mobile/sands-talk.git
git fetch --all

#git rebase --abort
BranchList=`git branch -r -l`
for Branch in $BranchList; do
    if [[ $Branch == "origin/feature/"* ]]
    then
        echo ''
        echo "start rebase ${Branch##*/}"
        git branch -d feature/${Branch##*/}
        git checkout -b feature/${Branch##*/} $Branch
        git pull
        git submodule init
        git submodule update
        git rebase origin/develop
        if [ $? != 0 ];then
            git rebase --abort
            exit 1;
        fi
        #git push
        echo "end rebase ${Branch##*/}"
        echo ''
    fi
done

git checkout develop
