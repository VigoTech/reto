@echo OFF
IF "%1"=="" GOTO :BADPARAM
IF "%2"=="" GOTO :BADPARAM

java -cp target\classes io.mset.orbit.Main %1 %2


:BADPARAM
echo Invalid arguments supplied.
echo Usage:
echo   ./run.bat <bucketSizes> <requestedValue>
echo:
echo   bucketSizes   : Comma separated list of bucket sizes.
echo   requestedValue: The value that is searched in a bucket.
echo:
echo Example:
echo   For bucket sizes 4 and 7, search for value 6.
echo   ./run.bat 4,7 6

:EOF


