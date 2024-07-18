# Java-RestAPI

API created in order to learn the basics of a Java Rest API. It is a simple CRUD of tasks.

### Techs used
- Java;
- Spring Boot;
- Hibernate;
- JPA;
- Junit;
- Mockito;
- Lombok;

## Requirements

In order to run this project you have to install:

- [Postman](https://www.postman.com/downloads/);
- [Docker](https://docs.docker.com/get-docker/);

## How to run

```bash
git clone https://github.com/AlexandrePossari/Java-RestApi.git
docker-compose up 
```

## API Documentation
After running the project, visit http://localhost:8080/swagger-ui/index.html in your browser to access the API documentation

You can also run the collection `Java API.postman_collection` located in the root directory. Just import it into Postman and run.

## Future improvements
Due to a change of priorities, I had no time to implement the following:

- Send messages with an Amazon SQS queue when the task pass the due date. (develop it with Localstack)
- Include authentication and authorization
