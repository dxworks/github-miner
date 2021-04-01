FROM openjdk:8


RUN mkdir /opt/githubminer

ADD bin/githubminer.sh /opt/githubminer
ADD github-miner-app/target/githubminer.jar /opt/githubminer/githubminer.jar

RUN chmod +x /opt/githubminer/githubminer.sh

WORKDIR /opt/githubminer
