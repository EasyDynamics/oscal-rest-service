# OSCAL REST Service
Initial implementation of some the [OSCAL REST API](https://github.com/EasyDynamics/oscal-rest)
which persists data as files in local directories.

## Project Organization

### oscal-rest-service-app

The Spring Boot application primarily consisting of Controllers that
delegate to the Service layer.

### oscal-object-service

The Service layer that defines manipulation of OSCAL POJOs and delegates
persistance to the Repository layer.

### oscal-data-repository-file-passthrough

A Repository layer implementation that persists OSCAL objects as local
JSON files.

### oscal-data-repository-commons

A lower-level repository dependency that
leverages [liboscal-java](https://github.com/usnistgov/liboscal-java)
to define the OSCAL POJOs.

## Local Development

### Prerequisites

 - Java 17 or newer
 - Maven

### Building and Running

To build and install all of the above artifacts, from the `oscal-rest-service` root dir run:

```
mvn clean install
```

To then launch the Spring Boot app:
```
cd oscal-rest-service-app
mvn spring-boot:run
```

You can view the API specification [on GitHub][rest-api-github] or
[using Swagger Editor][swagger-editor].

[rest-api-github]: https://github.com/EasyDynamics/oscal-rest
[swagger-editor]: https://editor.swagger.io/?url=https://raw.githubusercontent.com/EasyDynamics/oscal-rest/develop/openapi.yaml


## Contributing

For the process of Contributing to the project, please review
[CONTRIBUTING.md](https://github.com/EasyDynamics/.github/blob/main/CONTRIBUTING.md)
and adhere to the
[Code of Conduct](https://github.com/EasyDynamics/.github/blob/main/CODE_OF_CONDUCT.md).

## Licensing

For information on the project's license, please review the [LICENSE](/LICENSE) file.

