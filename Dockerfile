FROM ubuntu:18.04
RUN apt-get update &&\
    apt-get install -y openjdk-8-jdk
COPY ./target/generico-0.0.1-SNAPSHOT.jar /Projeto-generico/
ENTRYPOINT java -jar /Projeto-generico/generico-0.0.1-SNAPSHOT.jar 