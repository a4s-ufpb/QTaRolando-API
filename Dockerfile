FROM openjdk:8-jdk
EXPOSE 8081
COPY . .
RUN ./mvnw clean
RUN ./mvnw install
WORKDIR /target
ENTRYPOINT ["java", "-jar", "QTaRolando-API.jar"]