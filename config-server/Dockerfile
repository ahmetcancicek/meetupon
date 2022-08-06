FROM openjdk:17.0.2

ADD ./target/config-server.jar /app/

CMD ["java","-Xmx200m","-jar","/app/config-server.jar"]

EXPOSE 8888