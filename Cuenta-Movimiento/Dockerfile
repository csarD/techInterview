FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /cuentamovimiento
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src/ /cuentamovimiento/src/
RUN mvn -B package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
RUN addgroup --system app && adduser --system --ingroup app --uid 1000 app_runner

RUN chmod 777 /tmp
USER app_runner
WORKDIR /cuentamovimiento

COPY --from=build /cuentamovimiento/target/*.jar app.jar

EXPOSE 8140
CMD ["java", "-jar", "app.jar"]
