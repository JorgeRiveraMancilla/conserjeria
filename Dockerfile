FROM gradle:8.2-jdk17 AS build
WORKDIR /usr/app
COPY . .
RUN gradle shadowJar --no-daemon

FROM openjdk:17-jdk
COPY --from=build /usr/app/build/libs/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]

EXPOSE 7070