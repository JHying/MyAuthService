#依賴的父映像檔 
FROM maven:3.8-jdk-11-slim as builder
#作者 
MAINTAINER yinghan
WORKDIR /usr/myfile
#將 jar 包新增到父映像檔中（並重新取名） 
ADD auth-service.jar auth-service.jar
#容器宣告的埠
EXPOSE 8099
#容器啟動之後執行的命令（執行 jar 檔） 
CMD ["java","-jar","auth-service.jar"]