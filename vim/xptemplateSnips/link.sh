#!/usr/bin/env bash

VIM_PATH=`pwd`/..

if [[ ! -e $VIM_PATH/bundle/xptemplate ]]; then
    echo "xptemplate plugin not install"
    exit 0
fi

if [[ -e $VIM_PATH/bundle/xptemplate/personal ]]; then
    rm $VIM_PATH/bundle/xptemplate/personal
fi

ln -s $VIM_PATH/xptemplateSnips $VIM_PATH/bundle/xptemplate/personal

