#! /bin/sh


## declare an array variable
declare -a arr=(".vimrc"
                ".vimrc.local"
                ".vimrc.bundles"
                ".vimrc.bundles.local"
                ".vimrc.bundles.default"
                ".vimrc.before"
                ".vimrc.before.local"
                ".gvimrc"
                ".vim"
                ".xvimrc"
                ".zshrc")

## now loop through the above array
for i in "${arr[@]}"
do
    if [ -e ~/$i ];then
        echo "file: $i exist"
        echo "Please backup below files and rm them:"
        echo ${arr[@]}
        exit 0
    fi
done

for i in "${arr[@]}"
do
    ln -s ~/spf13-copy-vim/$i ~/$i
done

if [ ! -e ~/.gitmessage ];then
    ln -s ~/spf13-copy-vim/gitconfig/.gitconfig ~/.gitconfig
else
    echo "fail link .gitconfig; file exists"
fi

if [ ! -e ~/.gitmessage ];then
    ln -s ~/spf13-copy-vim/gitconfig/.gitmessage ~/.gitmessage
else 
    echo "fail link .gitmessage; file exists"
fi


