FROM tomcat
LABEL kunal
ADD https://monjeyb.s3.ap-south-1.amazonaws.com/studentapp.war /usr/local/tomcat/webapps/
EXPOSE 80