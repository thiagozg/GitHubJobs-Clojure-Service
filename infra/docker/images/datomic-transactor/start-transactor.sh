#!/bin/bash
set -e

PROPERTIES_FILE=/tmp/transactor.properties

cp $DATOMIC_CONFIG/transactor.properties $PROPERTIES_FILE

echo "license-key=$DATOMIC_LICENSE_KEY" >> $PROPERTIES_FILE

./bin/transactor $PROPERTIES_FILE