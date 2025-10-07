# Use Java runtime base image (you can switch to openjdk version you prefer)
#FROM openjdk:11-jre-slim

# Install Scala and Make
#RUN apt-get update && apt-get install -y scala make && apt-get clean

# Set working directory
#WORKDIR /src

# Copy all project files including Makefile
#COPY . /src

# Run make to compile the Scala project (usually compiles sources via Makefile)
#RUN make

# Default command to run the compiled Scala main class or jar
# Adjust if your Makefile produces a jar or class files
#CMD ["scala", "Main"]


FROM openjdk:11-jdk-slim

RUN apt-get update \
    && apt-get install -y curl gnupg \
    && echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list \
    && curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99e82a75642ac823" | apt-key add \
    && apt-get update \
    && apt-get install -y sbt \
    && apt-get install -y scala make \
    && apt-get clean

    # Set working directory
WORKDIR /app

# Copy all project files including Makefile
COPY . /app

# Run make to compile the Scala project (usually compiles sources via Makefile)
RUN make

RUN ls

# Default command to run the compiled Scala main class or jar
# Adjust if your Makefile produces a jar or class files
CMD ["sbt", "run"]