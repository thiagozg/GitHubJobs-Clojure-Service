# GitHub Jobs an Clojure Microservice

This Service was created with the objective of exploring how to code a CRUD application from zero using Clojure language and other tools, like:

* [Datomic](https://docs.datomic.com/on-prem/getting-started/brief-overview.html)
* [Docker](https://docs.docker.com/get-started/overview/)
* [Pedestal](https://github.com/pedestal/pedestal)
* [Component](https://github.com/stuartsierra/component)
* [Schema](https://github.com/plumatic/schema)
* Hexagonal Architecture

### How to run

1. First of all, you'll need [lein](https://leiningen.org/) configured
2. Create a [datomic account](https://my.datomic.com/login) if you still don't have one
3. Log into your datomic account and go to the [Licenses Page](https://my.datomic.com/account)
4. Click on the `Send License Key` button. This will send you and email with a license key. We will also use the `Download Key` you can find on this page.
5. Substitute `${hue}` for your credentials and execute:
```
make infra-credentials DATOMIC_LOGIN=${hue} \
DATOMIC_PASSWORD=${hue} \
DATOMIC_LICENSE_KEY=${hue} \
DATOMIC_VERSION=${hue} \
STORAGE_ADMIN_PASSWORD=${hue} \
STORAGE_DATOMIC_PASSWORD=${hue}
```
- DATOMIC_LOGIN is the email you have used to create your datomic account on step 2
- DATOMIC_PASSWORD is the `Download Key` you can find on step 3.
- DATOMIC_LICENSE_KEY is what was sent to you on step 4 without the `license_key` key and with no line breaks.
- DATOMIC_VERSION should be the same you use on `project.clj`
- STORAGE_ADMIN_PASSWORD and STORAGE_DATOMIC_PASSWORD can be anything you want.

Example:
```
make infra-credentials DATOMIC_LOGIN=your-datomic-account@email.com \
DATOMIC_PASSWORD=aed2a94a-60e2-11eb-ae93-0242ac130002 \
DATOMIC_LICENSE_KEY=32hdd9qhd38h33....h3297hd2o23d \
DATOMIC_VERSION=0.9.6045 \
STORAGE_ADMIN_PASSWORD=bla \
STORAGE_DATOMIC_PASSWORD=ble
```
7. Make sure you have maven installed on your machine. If you don't have it, you can install it via `brew install maven` or your preferred method.
6. Go to [datomic download page](https://my.datomic.com/downloads/pro) and download `datomic-pro-0.9.6045.zip`
7. Unzip the file you have just downloaded, open a terminal window inside the folder you have just unziped and run:
```bash
mvn install
./bin/maven-install
```
4. Execute `make infra-run`
5. Execute `make deps`
6. Execute `make run`

### How to play
You can import [Postman Collection](https://github.com/thiagozg/GitHubJobs-Clojure-Service/blob/master/GitHub-Jobs.postman_collection.json) to your postman app and make HTTP requests.
