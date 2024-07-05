FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /security-ms
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src/ /security-ms/src/
RUN mvn -B package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
RUN addgroup --system app && adduser --system --ingroup app app_runner
USER app_runner
WORKDIR /security-ms

COPY --from=build /security-ms/target/*.jar app.jar

EXPOSE 8120
CMD ["java", "-jar", "app.jar"]



