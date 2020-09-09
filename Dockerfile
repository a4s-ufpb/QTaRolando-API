FROM openjdk:8-jdk
EXPOSE 8080
COPY . .
RUN ./mvnw clean
RUN ./mvnw install
WORKDIR /target
ENTRYPOINT ["java", "-jar", "QTaRolando-API.jar"]