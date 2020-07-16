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
2. [Datomic Credentials](https://www.datomic.com/get-datomic.html) to download Pro version
3. Substitute `${hue}` for your credentials and execute:
```
make infra-credentials DATOMIC_LOGIN=${hue} \
DATOMIC_PASSWORD=${hue} \
DATOMIC_LICENSE_KEY=${hue} \
DATOMIC_VERSION=${hue} \
STORAGE_ADMIN_PASSWORD=${hue} \
STORAGE_DATOMIC_PASSWORD=${hue}
```
4. Execute `make infra-run`
5. Execute `make deps`
6. Execute `make run`

### How to play
You can import [Postman Collection](https://github.com/thiagozg/GitHubJobs-Clojure-Service/blob/master/GitHub-Jobs.postman_collection.json) to your postman app and make HTTP requests.
