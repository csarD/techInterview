FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /apigateway
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src/ /apigateway/src/
RUN mvn -B package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
RUN addgroup --system app && adduser --system --ingroup app app_runner
USER app_runner
WORKDIR /apigateway

COPY --from=build /apigateway/target/*.jar app.jar
COPY certificate.p12 /apigateway/certificate.p12

EXPOSE 8765
CMD ["java", "-jar", "app.jar"]

