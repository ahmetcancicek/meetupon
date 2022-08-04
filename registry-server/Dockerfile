FROM openjdk:17.0.2

ADD ./target/registry-server.jar /app/

CMD ["java","-Xmx200m","-jar","/app/registry-server.jar"]

EXPOSE 8761