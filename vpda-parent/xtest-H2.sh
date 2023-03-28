#! /bin/sh
BUILD_DIR=`dirname $0`
xvfb-run $BUILD_DIR/test-H2.sh $@
