#! /bin/sh
if [ $# -eq 1 ] ;then
    glassfishHome=$1
    else
    glassfishHome=/home/kitko/tools/glassfish5
fi
echo "Will clean glassfish logs in ${glassfishHome}"
echo "Running as $USER"

rm -rf ${glassfishHome}/glassfish/domains/build-1-default-it/logs
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-it/generated
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-it/osgi-cache
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-it/session-store

rm -rf ${glassfishHome}/glassfish/domains/build-1-default-ft/logs
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-ft/generated
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-ft/osgi-cache
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-ft/session-store

rm -rf ${glassfishHome}/glassfish/domains/build-1-default-cd-ft/logs
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-cd-ft/generated
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-cd-ft/osgi-cache
rm -rf ${glassfishHome}/glassfish/domains/build-1-default-cd-ft/session-store

rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i1/logs
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i1/generated
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i1/osgi-cache
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i1/session-store

rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i2/logs
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i2/generated
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i2/osgi-cache
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-default-cd-ft/i2/session-store

rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-it/logs
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-it/generated
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-it/osgi-cache
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-it/session-store

rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-ft/logs
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-ft/generated
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-ft/osgi-cache
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-ft/session-store

rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-cd-ft/logs
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-cd-ft/generated
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-cd-ft/osgi-cache
rm -rf ${glassfishHome}/glassfish/domains/build-1-samples-emp-cd-ft/session-store

rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i1/logs
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i1/generated
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i1/osgi-cache
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i1/session-store

rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i2/logs
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i2/generated
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i2/osgi-cache
rm -rf ${glassfishHome}/glassfish/nodes/localhost-build-1-samples-emp-cd-ft/i2/session-store
