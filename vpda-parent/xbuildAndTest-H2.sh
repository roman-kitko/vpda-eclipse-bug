#! /bin/sh
BUILD_DIR=`dirname $0`
xvfb-run $BUILD_DIR/buildAndTest-H2.sh $@
