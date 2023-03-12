FROM maven:3.8.3-openjdk-17
WORKDIR /vehicle-gateway
COPY pom.xml .
COPY commons-dto/pom.xml ./commons-dto/pom.xml
COPY rest/pom.xml ./rest/pom.xml
COPY tcp/pom.xml ./tcp/pom.xml
RUN mvn dependency:go-offline
COPY commons-dto/src ./commons-dto/src
COPY rest/src ./rest/src
COPY tcp/src ./tcp/src
RUN mvn package
