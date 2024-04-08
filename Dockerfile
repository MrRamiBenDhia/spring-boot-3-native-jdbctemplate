FROM ubuntu:24.04

LABEL authors="rami.bendhia"

# RUN apt-get -y update && apt-get -y upgrade
COPY target/spring-native-jdbctemplate /

RUN echo 'Test message printed during Docker build from spring boot native'
CMD ["/spring-native-jdbctemplate"]
