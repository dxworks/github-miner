FROM openjdk:8


RUN mkdir /opt/githubminer

ARG JAR_FILE

ADD bin/githubminer.sh /opt/githubminer
ADD target/${JAR_FILE} /opt/githubminer/githubminer.jar

RUN chmod +x /opt/githubminer/githubminer.sh

WORKDIR /opt/githubminer
