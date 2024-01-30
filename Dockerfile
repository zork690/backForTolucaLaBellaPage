FROM openjdk:8
VOLUME /tmp
EXPOSE 8082
ADD ./target/BackForTolucaLaBellaPage.war BackForTolucaLaBellaPage.war
ENTRYPOINT ["java", "-jar", "/BackForTolucaLaBellaPage.war"]
