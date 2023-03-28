#! /bin/sh
BUILD_DIR=`dirname $0`
sh $BUILD_DIR/clearGlassfishLogsAndGenerated.sh
mvn site:site site:stage -f $BUILD_DIR/pom.xml $@

