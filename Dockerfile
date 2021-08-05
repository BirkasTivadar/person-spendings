FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/person-spendings-0.0.1-SNAPSHOT.jar /opt/app/person-spendings.jar
CMD ["java", "-jar","opt/app/person-spendings.jar"]