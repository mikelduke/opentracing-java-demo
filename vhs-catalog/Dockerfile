from openjdk:8-jdk-alpine

EXPOSE 4567

COPY build/libs/* /apps/
WORKDIR /apps/
CMD ["java", "-jar", "/apps/vhs-catalog-all-0.0.1-SNAPSHOT.jar"]
