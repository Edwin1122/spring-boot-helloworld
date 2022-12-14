#
# Build stage
#

FROM maven:3.6.3-jdk-11-slim as build
WORKDIR usr/src/app
COPY . ./
RUN mvn clean package


#
# Package stage
#


FROM openjdk:11-jre-slim
ARG JAR_NAME="spring-boot-helloworld-0.0.1-SNAPSHOT"
WORKDIR /urs/src/app
COPY --from=build /usr/src/app/target/${JAR_NAME}.jar ./app.jar
CMD ["java", "-jar", "./app.jar"]