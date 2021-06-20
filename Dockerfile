FROM java:8

ADD ./target/*.jar /home/springboot/app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/springboot/app.jar","--spring.profiles.active=sit"]