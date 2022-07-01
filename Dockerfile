FROM openjdk:8-jdk-alpine

MAINTAINER JohnYehyo <johnyehyo@hotmail.com>

LABEL description="邮箱客户端"

ENV MYPATH /opt/email

WORKDIR $MYPATH

ADD rjsoft-email.jar email.jar

VOLUME $MYPATH

EXPOSE 8187

ENTRYPOINT ["java", "-Xms256m -Xmx256m", "-jar","/opt/email/email.jar"]
