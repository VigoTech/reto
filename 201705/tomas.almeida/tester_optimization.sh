#!/usr/bin/env bash

SOLUTION_FOUND=0
SOLUTION_NOTFOUND=1

TESTS_SIZE=100
elapsed=0
echo "Doing test:"
for ((i=0;i<=$TESTS_SIZE;i++)); do
  printf "%02d" $i;
  begin=`perl -MTime::HiRes -e 'printf("%.0f\n",Time::HiRes::time()*1000)'`
  $1 `./inputGenerator.py -90000 90000 1000` > /dev/null
  end=`perl -MTime::HiRes -e 'printf("%.0f\n",Time::HiRes::time()*1000)'`
  echo -n "[$(($end - $begin))] "
  elapsed=`awk "BEGIN {print ${elapsed} + (${end} - ${begin})}"`
done
echo ""
awk "BEGIN {print ${elapsed}/${TESTS_SIZE}}"
