##Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

MAINTAINER sergshamiryan

##Copy jar from target do the docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

##Execute the applicaton
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]