FROM openjdk:11
VOLUME /tmp
ADD target/block-chain-0.0.1-SNAPSHOT.jar app.jar
ADD src/main/resources resources
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java" ,"-jar", "/app.jar"]
