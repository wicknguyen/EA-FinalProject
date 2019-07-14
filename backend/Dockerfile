FROM openjdk:8-alpine

RUN mkdir -p /app
ENV PROJECT_HOME /app

COPY target/social-network-1.0-SNAPSHOT-spring-boot.jar $PROJECT_HOME/spring-boot.jar

WORKDIR $PROJECT_HOME

#ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo-demo/test", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
CMD ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "./spring-boot.jar"]