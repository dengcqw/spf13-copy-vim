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
plugins=(git brew common-alias dirhistory encode64 github history jsontools npm osx sudo urltools vi-mode web-search)

# User configuration

export PATH=/usr/sbin:/sbin/usr/bin:$HOME/bin:/usr/local/bin:$PATH
# export MANPATH="/usr/local/man:$MANPATH"

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
AUTOJUMP_PATH="/usr/local/Cellar/autojump/22.2.4/etc"
[[ -s /usr/local/Cellar/autojump/22.2.4/etc/profile.d/autojump.sh ]] && . /usr/local/Cellar/autojump/22.2.4/etc/profile.d/autojump.sh

alias cls='clear'
alias ll='ls -l'
alias la='ls -a'
alias vi='vim'
alias javac="javac -J-Dfile.encoding=utf8"
alias grep="grep --color=auto"
alias -s html=mvim   # 在命令行直接输入后缀为 html 的文件名，会在 mvim 中打开
alias -s rb=mvim
alias -s py=mvim
alias -s js=mvim
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

alias djgrep='grep --color=auto -r -n ./* -e '
alias rm="trash"

export EDITOR=mvim
alias pushToGithub='git push -u origin master'
source /usr/local/Cellar/autojump/22.2.4/etc/autojump.sh

#alias for cnpm
alias cnpm="npm --registry=https://registry.npm.taobao.org \
  --cache=$HOME/.npm/.cache/cnpm \
  --disturl=https://npm.taobao.org/dist \
  --userconfig=$HOME/.cnpmrc"

alias rnstartAtroot="react-native start --root "
alias npmi="npm install --save "

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
alias updateXcodeUUID="XCODEUUID=`defaults read /Applications/Xcode.app/Contents/Info DVTPlugInCompatibilityUUID` \
                  for f in ~/Library/Application\ Support/Developer/Shared/Xcode/Plug-ins/*;   \
                  do \
                      defaults write "$f/Contents/Info" DVTPlugInCompatibilityUUIDs -array-add $XCODEUUID;  \
                  done \
                  unset XCODEUUID;"

# Remove some line in a file. e.g. clear history file
# http://stackoverflow.com/a/5413132
# see scripts/clearHistory.sh

# Find file
alias djFindWithKeyword="tree -f -L 5 | grep "

alias lnNodeModule='sh ~/spf13-copy-vim/scripts/link-node-module.sh'

source ~/spf13-copy-vim/.zshrc.local
