FROM openjdk:11-jre

COPY target/codingame_challenge-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "codingame_challenge-0.0.1-SNAPSHOT.jar"]
