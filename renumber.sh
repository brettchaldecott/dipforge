#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
    echo "arguments <old version> <new version>"
    exit -1
fi

echo "Re-numbering from $1 to $2"

find ./ -name "build.gradle" | xargs grep "$1"
find ./ -name "build.gradle" | xargs sed -i "s/$1/$2/"
find ./ -name "build.gradle" | xargs grep "$2"

find ./release/ -name "*.xml" | xargs grep "$1"
find ./release/ -name "*.xml" | xargs sed -i "s/$1/$2/"
find ./release/ -name "*.xml" | xargs grep "$2"

find ./release/ -name "*.gsp" | xargs grep "$1"
find ./release/ -name "*.gsp" | xargs sed -i "s/$1/$2/"
find ./release/ -name "*.gsp" | xargs grep "$2"

