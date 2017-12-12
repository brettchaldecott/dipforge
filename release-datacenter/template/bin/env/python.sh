#!/bin/bash


if type "jep" > /dev/null; then
    PYTHON_DIST=($(pip show jep -f | grep Location | tr ": " "\n"))
    PYTHON_LIB=($(ldconfig -p | grep libpython2.7.so ))

    LD_LIBRARY_PATH="/usr/lib:${PYTHON_DIST[1]}/"; export LD_LIBRARY_PATH
    LD_PRELOAD="${PYTHON_LIB[3]}"; export LD_PRELOAD
    export JAVA_OPTS="$JAVA_OPTS -Djava.library.path=${PYTHON_DIST[1]}/jep/"


    echo "LD_LIBRARY_PATH ${LD_LIBRARY_PATH}"
    echo "LD_PRELOAD ${LD_PRELOAD}"

fi
