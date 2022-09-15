FROM openjdk:20-oracle
COPY . /src/rs3/
WORKDIR /src/rs3/
EXPOSE 8080
ENTRYPOINT [ "MainController.java" ]