# Spring experiments

This repository is a random collection of "I wonder how feature X works with Spring/Java"

## Basic features

Migrations are implemented using [liquibase](https://www.liquibase.org/).

Database operations are performed using [jOOQ](https://www.jooq.org/), which builds the Java API by scanning the database schema.

Most DTOs are implemented using [immutables](https://immutables.github.io/).

## Bitemporal tables

Support for bitemporal tables is provided by [temporal tables](https://github.com/nearform/temporal_tables).

## Hibernate entities

Although not actually used in this project, Hibernate entities are generated from the database schema using `org.hibernate.tool/hibernate-tools-maven` and the `hbm2java` goal.

Tässä on ensimmäinen rivi, mutta muutettuna.

Tässä on toinen rivi, mutta muutettuna.

Tässä on kolmas rivi.

## GraphQL

A simple GraphQL API is implemented with `com.graphql-java-kickstart/graphql-spring-boot-starter` ([github](https://github.com/graphql-java-kickstart/graphql-spring-boot), [docs](https://www.graphql-java-kickstart.com/spring-boot/)), which is built upon `com.graphql-java/graphql-java` ([github](https://github.com/graphql-java/graphql-java), [docs](https://www.graphql-java.com/documentation/v16/)).

An embedded [Altair](https://altair.sirmuel.design/) UI is provided with `com.graphql-java-kickstart/altair-spring-boot-starter` and is available at [http://localhost:8080/altair](http://localhost:8080/altair).

Project contains

- An example of a custom scalar (`UUID`)
- Asynchronous batch loading to avoid N+1 problems (`CompanyDataLoader`)
