" brew install clang-format
" invoke clang-format with G=gg or other = indent options
autocmd FileType c setlocal equalprg=clang-format\ -style=file\ -assume-filename=%
autocmd FileType cpp setlocal equalprg=clang-format\ -style=file\ -assume-filename=%
autocmd FileType java setlocal equalprg=clang-format\ -style=file\ -assume-filename=%
autocmd FileType swift setlocal equalprg=clang-format\ -style=file
autocmd FileType objc setlocal equalprg=clang-format\ -style=file\ -assume-filename=%
