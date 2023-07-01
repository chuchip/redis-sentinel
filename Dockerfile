FROM amazoncorretto:17
COPY ./build/libs/redis-cluster-0.0.1-SNAPSHOT.jar /build/spring.jar

ENTRYPOINT ["java",  "-jar","/build/spring.jar"]