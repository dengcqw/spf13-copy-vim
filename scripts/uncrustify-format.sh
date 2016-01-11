#!/usr/bin/env bash

(find . -name "*.h" -o -name "*.c" -o -name "*.cpp" -o -name "*.m" -o -name "*.mm") \
| while read -r line
    do
        uncrustify -c ~/spf13-copy-vim/scripts/uncrustify_objc_c.cfg --replace --no-backup $line 
        echo "Name read from file: $line"
    done

