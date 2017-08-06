find . -print0 | while IFS= read -r -d '' file
do 
    echo "$file"
done


or

function traverse() {
for file in *
do
    if [ ! -d "${file}" ] ; then
        echo "${file} is a file"
        mv ${file} ipad-${file}
    else
        echo "entering recursion with: ${file}"
        cd ${file}
        traverse "${file}"
    fi
done
cd -
}

function main() {
    cd $1
    traverse "$1"
}

main "."
