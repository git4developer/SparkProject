FROM openjdk:8
EXPOSE 1234
ADD target/sparkproject.jar sparkproject.jar
ENTRYPOINT ["java","-jar","/sparkproject.jar"]