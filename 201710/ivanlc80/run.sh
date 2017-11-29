#!/bin/bash
if [ "$#" -le  "1" ]
   then
     echo "Invalid arguments supplied."
     echo "Usage:"
     echo "  ./run.sh <bucketSizes> <requestedValue>"
     echo ""
     echo "  bucketSizes   : Comma separated list of bucket sizes."
     echo "  requestedValue: The value that is searched in a bucket."
     echo ""
     echo "Example:"
     echo "  For bucket sizes 4 and 7, search for value 6."
     echo "  ./run.sh 4,7 6"
     exit 1
 fi
java -cp target/classes io.mset.orbit.Main $1 $2