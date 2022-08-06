FROM openjdk:17.0.2

ADD ./target/user-service.jar /app/

CMD ["java","-Xmx200m","-jar","/app/user-service.jar"]

EXPOSE 8585