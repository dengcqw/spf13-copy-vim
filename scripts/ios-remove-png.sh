# delete useless png
for i in `find . -name "*.png" -o -name "*.jpg"`; do
    file=`basename -s .jpg "$i" | xargs basename -s .png | xargs basename -s @2x`
    result=`ack -i "$file"`
    if [ -z "$result" ]; then
        echo "$i";
        # rm "$i"
    fi
done
