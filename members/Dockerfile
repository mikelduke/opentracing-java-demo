from openjdk:8-jdk-alpine

EXPOSE 8080

COPY build/libs/* /apps/
WORKDIR /apps/
CMD ["java", "-jar", "/apps/members-0.0.1-SNAPSHOT.jar"]
