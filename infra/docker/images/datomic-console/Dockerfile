# Based on: https://github.com/pointslope/docker-datomic-console
# This version uses environment variables for credentials
FROM clojure:lein-2.6.1-alpine

ARG DATOMIC_LOGIN
ARG DATOMIC_PASSWORD
ARG DATOMIC_VERSION

ENV DATOMIC_HOME /opt/datomic-pro-$DATOMIC_VERSION

RUN apk add --no-cache unzip curl

RUN curl -u $DATOMIC_LOGIN:$DATOMIC_PASSWORD -SL https://my.datomic.com/repo/com/datomic/datomic-pro/$DATOMIC_VERSION/datomic-pro-$DATOMIC_VERSION.zip -o /tmp/datomic.zip \
&& unzip /tmp/datomic.zip -d /opt \
&& rm -f /tmp/datomic.zip

WORKDIR $DATOMIC_HOME

ENTRYPOINT ["bin/console", "-p", "9000"]

EXPOSE 9000
