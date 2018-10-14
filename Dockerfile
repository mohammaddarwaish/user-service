FROM openjdk:8-jdk-slim

WORKDIR /opt/workspace
COPY . /opt/workspace
RUN ./gradlew build

ENV PACKAGE_NAME user-service

RUN mkdir -p /opt/final/${PACKAGE_NAME} \
    && cp /opt/workspace/build/libs/${PACKAGE_NAME}-*.jar /opt/final/${PACKAGE_NAME} \
    && cd /opt/final/${PACKAGE_NAME} \
    && mv ${PACKAGE_NAME}-*.jar ${PACKAGE_NAME}.jar

# The runnable container.
FROM openjdk:8-jdk-slim

COPY --from=0 /opt/final /opt/final
WORKDIR /opt/final/user-service

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","user-service.jar"]

EXPOSE 8080