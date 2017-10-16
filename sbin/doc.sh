#!/usr/bin/env bash

function doc(){

    SOURCE="${BASH_SOURCE[0]}"

    while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
      DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
      SOURCE="$(readlink "$SOURCE")"
      [[ ${SOURCE} != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
    done
    DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

    cd ${DIR}/../

    gradle fatJar
    java -jar ext/jaxrs-analyzer.jar -b asciidoc \
        -n "Hale" \
        -o "doc/API.adoc" \
        -d "127.0.0.1" \
        -cp build/libs/hale-all-1.0-SNAPSHOT.jar build/classes/main/
}

doc
