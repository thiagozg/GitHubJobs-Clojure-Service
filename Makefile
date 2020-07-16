.PHONY: clean test test-clj

# Based on: https://github.com/den1k/vimsical/blob/master/Makefile

#
# Deps
#
deps:
	lein deps

#
# Lein
#
clean:
	lein clean

#
# Test
#
test: test-clj

test-clj: clean
	lein test

#
# Build
#
build: build-clj

build-clj: clean
	lein uberjar

#
# Setup credentials
#
infra-credentials:
ifeq ($(DATOMIC_LOGIN),)
	$(error DATOMIC_LOGIN is undefined)
endif
ifeq ($(DATOMIC_PASSWORD),)
	$(error DATOMIC_PASSWORD is undefined)
endif
ifeq ($(DATOMIC_LICENSE_KEY),)
	$(error DATOMIC_LICENSE_KEY is undefined)
endif
ifeq ($(DATOMIC_VERSION),)
	$(error DATOMIC_VERSION is undefined)
endif
ifeq ($(STORAGE_ADMIN_PASSWORD),)
	$(error STORAGE_ADMIN_PASSWORD is undefined)
endif
ifeq ($(STORAGE_DATOMIC_PASSWORD),)
	$(error STORAGE_DATOMIC_PASSWORD is undefined)
endif
	echo "DATOMIC_LOGIN=${DATOMIC_LOGIN}" >> infra/.env
	echo "DATOMIC_PASSWORD=${DATOMIC_PASSWORD}" >> infra/.env
	echo "DATOMIC_LICENSE_KEY=${DATOMIC_LICENSE_KEY}" >> infra/.env
	echo "DATOMIC_VERSION=${DATOMIC_VERSION}" >> infra/.env
	echo "STORAGE_ADMIN_PASSWORD=${STORAGE_ADMIN_PASSWORD}" >> infra/.env
	echo "STORAGE_DATOMIC_PASSWORD=${STORAGE_DATOMIC_PASSWORD}" >> infra/.env
	echo "{:datomic-secret-password \"${STORAGE_DATOMIC_PASSWORD}\"}" >> .lein-env

infra-start: infra/.env
	cd infra && docker-compose up -d

infra-logs:
	cd infra && docker-compose logs -f

infra-build: build
	cd infra && docker-compose build --no-cache datomic console

infra-stop:
	cd infra && docker-compose down -v --remove-orphans

infra-run: infra-start infra-logs

#
# Start Backend and Infra
#
run: infra-start
	lein run

#
# Clean deps, project and run server
#
rebuild: clean deps build run
