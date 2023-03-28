#! /bin/sh
BUILD_DIR=`dirname $0`
mvn clean install -f $BUILD_DIR/pom.xml -DskipTests \
-Dvpda.package.skip \
-Dvpda.ear.all.skip \
-Dsamples-emp-server-package-plain-it.skip -Dsamples-emp-server-package-plain-ft.skip \
-Dsamples-emp-server-jboss-it.skip -Dsamples-emp-server-jboss-ft.skip \
-Dsamples-emp-server-glassfish-it.skip -Dsamples-emp-server-glassfish-ft.skip \
-Dsamples.emp.package.server.skip -Dsamples-package.skip \
-Dvpda.package.all.skip -Dvpda-installer-izpack.skip $@

