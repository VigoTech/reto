#!/bin/bash

for f in tests/ok/in_*.txt; do
    echo $f
    out=$(echo $f | sed 's/in_/out_/');

    cat tests/ok/in_1.txt | scala indent.scala > tests/ok/tmp.txt
    diff tests/ok/tmp.txt $out > /dev/null 2>&1
    if [ "$?" != "0" ]; then
        echo "Error testing $f"
    fi
    rm tests/ok/tmp.txt
done

for f in tests/ko/in_bad_*.txt; do
    echo $f
    cat $f | scala indent.scala > /dev/null 2>&1
    if [ "$?" != "1" ]; then
        echo "Error testing $f"
    fi
done
