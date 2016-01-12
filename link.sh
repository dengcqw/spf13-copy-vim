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
    distPath=~/$i

    # link exist then continue
    if [ -L $distPath ]; then
        echo "Link exists to $distPath"
        continue
    fi

    # file exist, back up it
    if [ -e $distPath ];then
        echo "File exists: $i"
        echo "Backup to $i.bk"
        mv $distPath "$distPath.bk"
    fi

    # create link
    ln -s ~/spf13-copy-vim/$i $distPath
done

if [ ! -e ~/.gitconfig ];then
    ln -s ~/spf13-copy-vim/gitconfig/.gitconfig ~/.gitconfig
else
    echo "fail link .gitconfig; file exists"
fi

if [ ! -e ~/.gitmessage ];then
    ln -s ~/spf13-copy-vim/gitconfig/.gitmessage ~/.gitmessage
else
    echo "fail link .gitmessage; file exists"
fi

ZSHRC_LOCAL=~/spf13-copy-vim/.zshrc.local

if [ ! -e $ZSHRC_LOCAL ];then
    touch $ZSHRC_LOCAL
fi
