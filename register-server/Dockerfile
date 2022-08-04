FROM openjdk:17.0.2

ADD ./target/register-server.jar /app/

CMD ["java","-Xmx200m","-jar","/app/register-server.jar"]

EXPOSE 8761