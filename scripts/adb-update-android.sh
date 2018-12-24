#!/usr/bin/env bash

adb mount

rc=$?;
if [[ $rc != 0 ]]; then
    echo "adb mount fail"
    exit $rc;
fi

adb push key data/local/tmp

if [[ $rc != 0 ]]; then
    echo "adb push key fail"
    exit $rc;
fi

adb rmount
adb root

if [[ $rc != 0 ]]; then
    echo "adb root fail"
    exit $rc;
fi


