#!/usr/bin/env bash

if [ -z $RNNodeModulesPATH ]
then
    echo "Usage: add \"export RNNodeModulesPATH=(your node modules path)\" to .zshrc.local"
    exit 0
fi

ln -s $RNNodeModulesPATH `pwd`/node_modules
