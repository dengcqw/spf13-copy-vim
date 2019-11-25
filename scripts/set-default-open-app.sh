#!/bin/bash

#========================================================================
#  FILE:  set_default_apps.sh
#  DESCRIPTION:  Changes default apps for extensions
#  AUTHOR:  Scott Granneman (RSG), scott@chainsawonatireswing.com
#  COMPANY:  Chainsaw on a Tire Swing (http://ChainsawOnATireSwing.com)
#  VERSION:  0.1
#  CREATED:  09/17/2012 21:44:01 CDT
#  REVISION:   
#========================================================================

{ cat <<eof
org.vim.MacVim:h
org.vim.MacVim:m
org.vim.MacVim:c
org.vim.MacVim:cpp
org.vim.MacVim:cc
org.vim.MacVim:mm
eof
} | grep . |
while IFS=$':' read bundle_id extension ; do
  # Grep to see if Bundle ID exists, sending stdout to /dev/null
  /System/Library/Frameworks/CoreServices.framework/Versions/A/Frameworks/LaunchServices.framework/Versions/A/Support/lsregister -dump | grep $bundle_id > /dev/null  
  # Save exit status (0=success & 1=failure)
  status=$?
  # If exit status failed, notify me & exit; if not, change default app for extension
  if test $status -eq 1 ; then
    echo "$bundle_id doesn't exist! Fix the script!"
    exit
  else
    echo -e "\nChanging $extension so it opens with $bundle_id …\n"
    duti -s $bundle_id .$extension all
    echo -e "Here's proof…\n"
    duti -x $extension
    echo -e "\n----------"
  fi
done
