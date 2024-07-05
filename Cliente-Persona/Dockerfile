FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /clientepersona
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src/ /clientepersona/src/
RUN mvn -B package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
RUN addgroup --system app && adduser --system --ingroup app --uid 1000 app_runner

RUN chmod 777 /tmp
USER app_runner
WORKDIR /clientepersona

COPY --from=build /clientepersona/target/*.jar app.jar

EXPOSE 8130
CMD ["java", "-jar", "app.jar"]
