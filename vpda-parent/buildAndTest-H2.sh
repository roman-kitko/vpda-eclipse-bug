#! /bin/sh
BUILD_DIR=`dirname $0`
sh $BUILD_DIR/clearGlassfishLogsAndGenerated.sh
sh $BUILD_DIR/clearJbossLogsAndGenerated.sh
mvn clean install -f $BUILD_DIR/pom.xml $@

