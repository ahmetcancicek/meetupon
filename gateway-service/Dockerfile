FROM openjdk:17.0.2

ADD ./target/gateway-service.jar /app/

CMD ["java","-Xmx200m","-jar","/app/gateway-service.jar"]

EXPOSE 8585