# Media Asset Annotator Server

Backend module for the [Media Asset Annotator](https://github.com/ulcheyev/media-asset-annotator) frontend. This service
provides:

- Integration with [MediaCMS](https://mediacms.io/) (tightly integrated
  with [MediaCMS API](https://demo.mediacms.io/swagger/))
- Annotation persistence using **GraphDB**
- Integration with tools related to [Record Manager](https://github.com/kbss-cvut/record-manager-ui/tree/main)

![Build (main)](https://github.com/ulcheyev/media-asset-annotator-server/actions/workflows/build-and-push.yml/badge.svg?branch=main)

---

## Technology Stack

- Java 21
- Spring Boot 4.0.2
- JOPA 2.8.1

---

## Local Setup

Build the project:

```bash
mvn clean install
```

Run locally:

```bash
mvn spring-boot:run
```

By default the server runs on http://localhost:5040/annotator-server/api

---

## 🐳 Docker

The application is packaged as an executable Spring Boot fat jar. To build Docker image locally:

```bash
docker build -t media-asset-annotator-server .
```

Run container:

```bash
docker run -p 5040:5040 media-asset-annotator-server
```

## Configuration

- See [Spring Boot configuration file](src/main/resources/application.yml) for configuration.
- Refer to [Full Deployment Configuration](https://github.com/ulcheyev/media-asset-annotator/tree/main/deploy/full) to
  deploy the complete stack.
