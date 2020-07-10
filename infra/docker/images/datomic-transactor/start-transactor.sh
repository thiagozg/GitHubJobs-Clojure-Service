#!/bin/sh
set -e

PROPERTIES_FILE=bin/transactor.properties

cp $DATOMIC_CONFIG/transactor.properties bin

echo "license-key=$DATOMIC_LICENSE_KEY" >> $PROPERTIES_FILE
echo "storage-admin-password=$STORAGE_ADMIN_PASSWORD" >> $PROPERTIES_FILE
echo "storage-datomic-password=$STORAGE_DATOMIC_PASSWORD" >> $PROPERTIES_FILE

chmod a+x bin/transactor
sh ./bin/transactor $PROPERTIES_FILE
