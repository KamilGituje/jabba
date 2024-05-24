FROM gradle:latest AS build
COPY . /demo
WORKDIR /demo
RUN gradle bootJar --no-daemon --stacktrace
FROM openjdk:21-jdk
COPY --from=build /demo/build/libs/demo-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-jar","/demo.jar"]