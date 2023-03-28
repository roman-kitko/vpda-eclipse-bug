#! /bin/sh
BUILD_DIR=`dirname $0`
sh $BUILD_DIR/clearGlassfishLogsAndGenerated.sh
sh $BUILD_DIR/clearJbossLogsAndGenerated.sh
PROJECTS="--projects org.vpda:samples-emp-server-package-plain-it,org.vpda:samples-emp-server-package-plain-ft,org.vpda:samples-emp-server-glassfish-it,org.vpda:samples-emp-server-glassfish-ft,org.vpda:samples-emp-server-jboss-it,org.vpda:samples-emp-server-jboss-ft"
SYSTEM_PROPERTIES=""
mvn clean install -f $BUILD_DIR/pom.xml $PROJECTS $SYSTEM_PROPERTIES $@

