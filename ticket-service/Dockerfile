FROM openjdk:17.0.2

ADD ./target/ticket-service.jar /app/

CMD ["java","-Xmx200m","-jar","/app/ticket-service.jar"]

EXPOSE 8585