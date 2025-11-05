# Build stage
FROM docker.io/library/maven:3.8.5-openjdk-17-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY settings.xml /usr/src/app
WORKDIR /usr/src/app
RUN java -version && mvn -v
RUN mvn --batch-mode -s /usr/src/app/settings.xml -f /usr/src/app/pom.xml package &&\
  mvn --batch-mode -s /usr/src/app/settings.xml -f /usr/src/app/pom.xml javadoc:javadoc

# Package stage
FROM docker.io/library/eclipse-temurin:17-alpine
LABEL maintainer="Mike Millson <mmillson@redhat.com>"
USER root
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update && \
  apt-get clean && rm -rf /var/lib/apt/lists/*

# Add krashpad user and group
RUN groupadd -g 30001 krashpad && \
  useradd --no-log-init -m -d /home/krashpad -u 30001 -g 30001 krashpad

COPY --from=build /usr/src/app/target/krashpad-2.0.1-SNAPSHOT.jar /home/krashpad/krashpad.jar

# Run everything as krashpad
USER krashpad
WORKDIR /home/krashpad

RUN mkdir -p /home/krashpad/files &&\
  chown -R 30001:30001 /home/krashpad/files

# Default home dir
ENV HOME=/home/krashpad

ENTRYPOINT ["java", "-jar", "krashpad.jar"]
