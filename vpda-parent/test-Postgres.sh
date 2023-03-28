#! /bin/sh
BUILD_DIR=`dirname $0`
sh $BUILD_DIR/clearGlassfishLogsAndGenerated.sh
PROJECTS="--projects org.vpda:samples-emp-server-package-it,org.vpda:samples-emp-server-package-ft,org.vpda:samples-emp-server-glassfish-it,org.vpda:samples-emp-server-glassfish-ft"
SYSTEM_PROPERTIES="-Dsamples-emp-server-package-plain-it.postgres -Dsamples-emp-server-package-plain-ft.postgres -Dsamples-emp-server-glassfish-it.postgres -Dsamples-emp-server-glassfish-ft.postgres"
mvn clean install -f $BUILD_DIR/pom.xml $PROJECTS $SYSTEM_PROPERTIES $@

