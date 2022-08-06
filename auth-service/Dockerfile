FROM openjdk:17.0.2

ADD ./target/auth-service.jar /app/

CMD ["java","-Xmx200m","-jar","/app/auth-service.jar"]

EXPOSE 8585