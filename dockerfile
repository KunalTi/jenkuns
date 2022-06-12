FROM centos
RUN cd /etc/yum.repos.d/
RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
RUN sed -i 's|#baseurl#http://mirror.centos.org|baseurl#http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*
RUN yum update -y
RUN yum install httpd -y
COPY index.html /var/www/html/
#WORKDIR /var/www/html
#USER ubuntu
#COPY httpd-foreground /usr/local/bin
EXPOSE 8080
CMD ["/usr/sbin/httpd", "-D", "FOREGROUND"]
###############################################3
FROM httpd:latest
COPY index.html /var/www/html/
EXPOSE 8080