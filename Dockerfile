FROM openjdk:8u121-jdk-alpine
ADD build/libs/microservice-0.1.0.jar app.jar
ADD docker/init.sh /
RUN sh -c 'touch /app.jar' && \
chmod +x /init.sh
EXPOSE 6969
EXPOSE 6970
ENTRYPOINT [ "java","-jar", "/app.jar" ]