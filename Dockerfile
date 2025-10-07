FROM openjdk:11-jdk-slim

RUN apt-get update \
    && apt-get install -y curl gnupg \
    && echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list \
    && curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99e82a75642ac823" | apt-key add \
    && apt-get update \
    && apt-get install -y sbt \
    && apt-get install -y scala make \
    && apt-get clean

WORKDIR /app

COPY . /app

RUN sbt compile

CMD ["sbt", "run"]