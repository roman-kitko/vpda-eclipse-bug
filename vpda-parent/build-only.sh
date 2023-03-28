#! /bin/sh
BUILD_DIR=`dirname $0`
mvn clean install -f $BUILD_DIR/pom.xml -DskipTests \
-Dvpda.ear.all.skip \
-Dsamples-emp-server-package-plain-it.skip -Dsamples-emp-server-package-plain-ft.skip \
-Dsamples-emp-server-jboss-it.skip -Dsamples-emp-server-jboss-ft.skip \
-Dsamples-emp-server-glassfish-it.skip -Dsamples-emp-server-glassfish-ft.skip $@

