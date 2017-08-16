# Path to your oh-my-zsh installation.
export ZSH=$HOME/.oh-my-zsh

# Set name of the theme to load.
# Look in ~/.oh-my-zsh/themes/
# Optionally, if you set this to "random", it'll load a random theme each
# time that oh-my-zsh is loaded.
ZSH_THEME="robbyrussell"

# Uncomment the following line to use case-sensitive completion.
# CASE_SENSITIVE="true"

# Uncomment the following line to use hyphen-insensitive completion. Case
# sensitive completion must be off. _ and - will be interchangeable.
# HYPHEN_INSENSITIVE="true"

# Uncomment the following line to disable bi-weekly auto-update checks.
# DISABLE_AUTO_UPDATE="true"

# Uncomment the following line to change how often to auto-update (in days).
# export UPDATE_ZSH_DAYS=13

# Uncomment the following line to disable colors in ls.
# DISABLE_LS_COLORS="true"

# Uncomment the following line to disable auto-setting terminal title.
# DISABLE_AUTO_TITLE="true"

# Uncomment the following line to enable command auto-correction.
# ENABLE_CORRECTION="true"

# Uncomment the following line to display red dots whilst waiting for completion.
# COMPLETION_WAITING_DOTS="true"

# Uncomment the following line if you want to disable marking untracked files
# under VCS as dirty. This makes repository status check for large repositories
# much, much faster.
# DISABLE_UNTRACKED_FILES_DIRTY="true"

# Uncomment the following line if you want to change the command execution time
# stamp shown in the history command output.
# The optional three formats: "mm/dd/yyyy"|"dd.mm.yyyy"|"yyyy-mm-dd"
# HIST_STAMPS="mm/dd/yyyy"

# Would you like to use another custom folder than $ZSH/custom?
# ZSH_CUSTOM=/path/to/new-custom-folder

# Which plugins would you like to load? (plugins can be found in ~/.oh-my-zsh/plugins/*)
# Custom plugins may be added to ~/.oh-my-zsh/custom/plugins/
# Example format: plugins=(rails git textmate ruby lighthouse)
# Add wisely, as too many plugins slow down shell startup.
# https://github.com/robbyrussell/oh-my-zsh/wiki/Plugins
plugins=(git brew common-alias dirhistory encode64 github history jsontools npm osx sudo urltools vi-mode xcode)

# User configuration

export PATH=/usr/sbin:/sbin/usr/bin:$HOME/bin:/usr/local/bin:$PATH:$HOME/spf13-copy-vim/bin:$HOME/Library/Android/sdk/platform-tools
# export MANPATH="/usr/local/man:$MANPATH"

export ANDROID_HOME=$HOME/Library/Android/sdk

source $ZSH/oh-my-zsh.sh

# You may need to manually set your language environment
# export LANG=en_US.UTF-8

# Preferred editor for local and remote sessions
# if [[ -n $SSH_CONNECTION ]]; then
#   export EDITOR='vim'
# else
#   export EDITOR='mvim'
# fi

# Compilation flags
# export ARCHFLAGS="-arch x86_64"

# ssh
# export SSH_KEY_PATH="~/.ssh/dsa_id"

# Set personal aliases, overriding those provided by oh-my-zsh libs,
# plugins, and themes. Aliases can be placed here, though oh-my-zsh
# users are encouraged to define aliases within the ZSH_CUSTOM folder.
# For a full list of active aliases, run `alias`.
#
# Example aliases
# alias zshconfig="mate ~/.zshrc"
# alias ohmyzsh="mate ~/.oh-my-zsh"
#

# autojump setting
[ -f /usr/local/etc/profile.d/autojump.sh ] && . /usr/local/etc/profile.d/autojump.sh

# brew install keith/formulae/zsh-xcode-completions
fpath=(/usr/local/share/zsh/site-functions $fpath)

# brew path
export brew_path=/usr/local/Cellar



alias cls='clear'
alias ll='ls -l'
alias la='ls -a'
alias vi='vim'
alias javac="javac -J-Dfile.encoding=utf8"
alias -s html=mvim   # 在命令行直接输入后缀为 html 的文件名，会在 mvim 中打开
alias -s rb=mvim
alias -s py=mvim
#alias -s js=mvim
alias -s c=mvim
alias -s java=mvim
alias -s txt=mvim
alias -s gz='tar -xzvf'
alias -s tgz='tar -xzvf'
alias -s zip='unzip'
alias -s bz2='tar -xjvf'

alias -s m=mvim
alias -s h=mvim
alias -s plist=mvim

# Custom alias {
    alias djgrep='grep --color=auto -r -n ./* -e '
    alias rm="trash"

    export EDITOR=mvim
    alias pushToGithub='git push -u origin master'
    alias ptg='git push -u origin master'

    #alias for cnpm
    alias cnpm="npm --registry=https://registry.npm.taobao.org \
      --cache=$HOME/.npm/.cache/cnpm \
      --disturl=https://npm.taobao.org/dist \
      --userconfig=$HOME/.cnpmrc"

    alias npmi="npm install --save "
    alias ns='npm start'
    alias ni='node-inspector --web-port 8082 &'

    alias vim='/Applications/MacVim.app/Contents/MacOS/Vim'

    alias djCopyPath='sh ~/spf13-copy-vim/bin/copy-file-path '

    alias carthage-ios='carthage update --platform iOS'
    alias srczshrc='source ~/.zshrc'

    alias djSwitchNetork='node ~/spf13-copy-vim/scripts/network_switch.js'

#}

# brew install coreutils
# use gnu tools
PATH="/usr/local/opt/coreutils/libexec/gnubin:$PATH"
MANPATH="/usr/local/opt/coreutils/libexec/gnuman:$MANPATH"


# Github
# push a exist git project to github
# 1. git remote add origin [github repo]
# 2. git pull origin master
# 3. git push origin master

# diff
# diff -u oldfile1 newfile2 > diff.txt

# Xcode plugin UUID
function updateXcodeUUID() {
    XCODEUUID=`defaults read /Applications/Xcode.app/Contents/Info DVTPlugInCompatibilityUUID`;

    for f in ~/Library/Application\ Support/Developer/Shared/Xcode/Plug-ins/*;
    do
        defaults write "$f/Contents/Info" DVTPlugInCompatibilityUUIDs -array-add $XCODEUUID;
    done
    unset XCODEUUID;
}

# Remove some line in a file. e.g. clear history file
# http://stackoverflow.com/a/5413132
# see scripts/clearHistory.sh

# Find file
alias djFindWithKeyword="tree -f -L 5 | grep "

alias toggleDockIcon='sh $HOME/spf13-copy-vim/scripts/toggleDockIcon.sh'

# Git short key {
    alias tgst='git status'
    alias tgs='git status'

    alias tgpl='git pull'
    alias tgp='git pull'

    alias tgps='git push'

    alias tgdf='git diff'
    alias tgd='git diff'

    alias tgadd='git add *'
    alias tga='git add *'

    alias tgc='git commit -v -m'
    alias tgc-a='git commit -v -a -m'

    alias tgbr='git branch'
    alias tgb='git branch'
    alias tgbr-a='git branch -a'
    alias tgco='git checkout'
    alias tgco-b='git checkout -b'
    alias tgco-t='git checkout -t'
    alias tgco-tb='git checkout --track -b'
    alias tglog='git log'
    alias tgl='git log'
    alias tglog-p='git log --pretty=format:"%h %s" --graph'
    alias tgcl='git clone '

    # undo the last commit
    alias tgr-soft='git reset --soft HEAD~' # 用于未提交到服务器上的commit
    # del the last commit
    alias tgr-hard='git reset --hard HEAD~'
    alias tgrevert='git revert HEAD~'  # 用一次新的commit来回滚之前的commit, 用于已经提交到服务器上的commit

    alias tgreview='git push origin HEAD:refs/for/master'
    alias tgpatch='git format-patch -1 HEAD'
    alias tgpushforce='git push --force' # dangerous
    alias tgsub='git submodule update --init --recursive'
#}


# Use local setting {
    ZSHRC_LOCAL="$HOME/spf13-copy-vim/.zshrc.local"
    if [ ! -e $ZSHRC_LOCAL ];then
        touch $ZSHRC_LOCAL
    fi
    source  $ZSHRC_LOCAL
#}


# gem source switch {
    function gem_offical_source() {
        gem sources --remove https://ruby.taobao.org/
        gem sources -a https://rubygems.org/
    }

    function gem_taobao_source() {
        gem sources --remove https://rubygems.org/
        gem sources -a https://ruby.taobao.org/
    }
#}


# react-native must run with the same path to node_modules/react-native
# RN_PATH must define in $ZSHRC_LOCAL {
    export RN_PATH=
    function linkReactNativeHere() {
        fromPath=$RN_PATH
        toPath=`pwd`/react-native
        ln -s $fromPath $toPath
    }

    function rnstartAtPrjRoot() {
        # if no arg, then use current path
        CurPath=`pwd`
        FullPath=`pwd`
        if [ "$1" != "." -a "$1" != "./" ];then
            FullPath=`pwd`/$1
        fi

        # change to root path
        react-native start --projectRoots $FullPath --root $RN_PATH
    }
# }

# Move all the files (MyApp.app, MyApp-dSYM.dSYM and MyApp-Crash-log.crash) into a Folder
# "djSymbolicatecrash [*.crash] [app] > output.txt"
export DEVELOPER_DIR=/Applications/Xcode.app/Contents/Developer
function djSymbolicatecrash() {
    #/Applications/Xcode.app/Contents/SharedFrameworks/DTDeviceKitBase.framework/Versions/A/Resources/symbolicatecrash $1 $2
    /Applications/Xcode.app/Contents/SharedFrameworks/DVTFoundation.framework/Versions/A/Resources/symbolicatecrash $1 $2
}

# cp file then jump to dest dir
function djcp() {
    cp $@
    mvim "${@:$#}"
}

# mv file then jump to dest dir
function djmv() {
    mv $@
    mvim "${@:$#}"
}

function djcd() {
     cd $@; ls;
}

function djpwd() {
     djpwdpath=`pwd`
     echo $djpwdpath
     echo $djpwdpath | clipcopy
}

# very useful tips

function vim-linedelete() {
     echo ":g/pattern/d  # delete lines that contain [pattern]"
     echo ":v/pattern/d or :g!/pattern/d  # delete lines that not contain [pattern]"
     echo ":g/^$/d      # delete empty lines"
     echo ":%s/^.*\(pattern\).*$/\1/g   # delete other words except [pattern]"
     echo "g/str1/,/str2/d      ## delete lines between line with [str1] and line with [str2]"
}

alias startSwiftCompleteServer='node $HOME/spf13-copy-vim/scripts/startSourceKittenDeamon.js'

# https://zhuanlan.zhihu.com/p/23823231
# plugin to iphone, run below to create virtual network interface, then use wireshark
# rvictl -s ba490377a9fc6df3243a343ae52aa76f8d0d6b71
# rvictl -x ba490377a9fc6df3243a343ae52aa76f8d0d6b71

alias mpi='make package install'

# Android {
    alias path-Android-support="cd /Users/dengjinlong/Library/Android/sdk/extras/android/m2repository/com/android/support"
#}

# find symbol in lib
# nm -A LibQYDlna_TVGUOAPP.a  | grep QYHttpFile

alias gr='./gradlew run'
alias gb='./gradlew build'
alias gt='./gradlew tasks'

#[[ -s "$HOME/.gvm/scripts/gvm" ]] && source "$HOME/.gvm/scripts/gvm"
# 安装godoc命令: go get golang.org/x/tools/cmd/godoc
# 开启http服务器: godoc -http=:8080

export GOPATH=/Users/dengjinlong/Documents/9-go

