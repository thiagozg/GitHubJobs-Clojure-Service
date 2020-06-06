FROM openjdk:8-alpine
MAINTAINER Thiago Zagui Giacomini <https://github.com/thiagozg>

ADD target/github-jobs-0.0.1-SNAPSHOT-standalone.jar /github-jobs/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/github-jobs/app.jar"]
