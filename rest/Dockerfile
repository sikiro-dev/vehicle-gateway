FROM vehicle-gateway:latest AS build

FROM openjdk:17
WORKDIR /app
COPY --from=build /vehicle-gateway/rest/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
