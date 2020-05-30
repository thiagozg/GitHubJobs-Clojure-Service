FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/github-jobs-0.0.1-SNAPSHOT-standalone.jar /github-jobs/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/github-jobs/app.jar"]
