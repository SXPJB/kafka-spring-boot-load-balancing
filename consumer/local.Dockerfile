FROM gradle:8.6.0-jdk17 AS build

WORKDIR /builder

COPY . /builder

RUN ./gradlew build -x test

FROM build

WORKDIR /app

COPY --from=build /builder/build/libs/*jar /app/consumer.jar

ENTRYPOINT ["java", "-jar", "consumer.jar"]