# Based on: https://github.com/pointslope/docker-datomic
# This version uses environment variables for credentials
FROM clojure:lein-2.6.1-alpine

ARG DATOMIC_LOGIN
ARG DATOMIC_PASSWORD
ARG DATOMIC_VERSION
ARG MAVEN_VERSION=3.6.3
ARG SHA_MAVEN=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

ENV DATOMIC_HOME    /opt/datomic-pro-$DATOMIC_VERSION
ENV DATOMIC_DATA    /var/datomic/data
ENV DATOMIC_LOG     /var/datomic/log
ENV DATOMIC_CONFIG  /var/datomic/config

VOLUME $DATOMIC_DATA
VOLUME $DATOMIC_LOG
VOLUME $DATOMIC_CONFIG

RUN apk add --no-cache unzip curl

# Create the directories, download maven, validate the download, install it, remove downloaded file and set links
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downloading maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Unziping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN curl -u $DATOMIC_LOGIN:$DATOMIC_PASSWORD -SL https://my.datomic.com/repo/com/datomic/datomic-pro/$DATOMIC_VERSION/datomic-pro-$DATOMIC_VERSION.zip -o /tmp/datomic.zip \
&& unzip /tmp/datomic.zip -d /opt \
&& rm -f /tmp/datomic.zip

WORKDIR $DATOMIC_HOME

RUN ./bin/maven-install
ADD ./images/datomic-transactor/start-transactor.sh .
RUN chmod -R 755 start-transactor.sh
CMD ./start-transactor.sh

EXPOSE 4334 4335 4336 9999
