FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /naming
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src/ /naming/src/
RUN mvn -B package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
RUN addgroup --system app && adduser --system --ingroup app app_runner
USER app_runner
WORKDIR /naming

COPY --from=build /naming/target/*.jar app.jar

EXPOSE 8761
CMD ["java", "-jar", "app.jar"]


