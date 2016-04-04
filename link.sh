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

if [ ! -e ~/.vim ];then
    ln -s ~/spf13-copy-vim/vim ~/.vim
fi

if [ ! -e ~/.gitconfig ];then
    cp ~/spf13-copy-vim/gitconfig/.gitconfig ~/.gitconfig
else
    echo "cp link .gitconfig; file exists"
fi

if [ ! -e ~/.gitmessage ];then
    cp ~/spf13-copy-vim/gitconfig/.gitmessage ~/.gitmessage
else
    echo "cp link .gitmessage; file exists"
fi

ZSHRC_LOCAL=~/spf13-copy-vim/.zshrc.local

if [ ! -e $ZSHRC_LOCAL ];then
    touch $ZSHRC_LOCAL
fi

nvim_path=`which nvim`
if [ ! -z "$nvim_path" ];then
    ln -s ~/spf13-copy-vim/vim ~/.config/nvim
    ln -s ~/.vimrc ~/.config/nvim/init.vim
fi

