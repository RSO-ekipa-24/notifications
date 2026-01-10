# Notifications :rotating_light:

## Overview

Right now, this service emails you after you create an account. In later stages, this service will be upgraded to send email notifications when you rent a property, when you receive a message inside the app etc.

### Branching Strategy

- main: The production-ready branch.
- dev: The integration branch for features and fixes, often considered the "next release" branch.
- feature/: Branches for developing new features. These branches are created from dev and merged back into dev when the feature is complete.
- bugfix/: Branches for fixing bugs in the dev branch.
- release/: Branches for preparing a new production release. These branches allow for last-minute fixes and preparing release notes.
- hotfix/: Branches for fixing critical issues in the main branch. These are created from main and merged back into both main and dev.


## Technology stack :computer:

| Category                  | Technology / Tool           |
|----------------------------|----------------------------|
| Backend framework          | Quarkus                    |
| Messaging / Event streaming| Apache Kafka               |
| Build tool                 | Maven                      |
| Containerization           | Docker                     |
| CI/CD Automation           | GitHub Actions             |


## Microservice relations

This service is a backend microservice that only communicates via Kafka.  
It does not expose any REST endpoints to external clients and has no direct dependencies on other microservices.

---

## Running the application

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/notifications-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


## Build and push the image to Google Registry :whale: 


First, you need to commit and push all the changes u made to Git!

Then extract your commit hash:

```bash
GIT_HASH=$(git rev-parse --short HEAD)
```
and `echo` it and confirm it matches the hash on GitHub UI.

1. Package the app - This generates the application files inside the target/ folder.


```bash
./mvnw clean package -Dquarkus.container-image.build=false -DskipTests
```

if it fails, you may need to run `chmod +x mvnw`


2. Build the local docker image and tag it for Google registry - we will tag it with the commit hash for easier rollbacks and to keep track.

```bash
docker build -f src/main/docker/Dockerfile.jvm -t europe-central2-docker.pkg.dev/artful-reactor-351917/essa-images/notifications-service:$GIT_HASH .
```

3. Push to the cloud:

```bash
docker push europe-central2-docker.pkg.dev/artful-reactor-351917/essa-images/notifications-service:$GIT_HASH
```

## Deploy via helm chart :arrow_up:

Move to `notifications/deploy/k8s/helm` and run:

```bash
helm upgrade --install notifications-release ./notifications-chart --set notifications.deployment.image.tag=$GIT_HASH
```
