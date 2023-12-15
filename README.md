# Spring Boot reactive ObservationConvention issue

This repository demonstrates a change between Spring Boot 3.1.6 and 3.2.0.
With Spring Boot 3.1.6, a Spring WebFlux application with Micrometer Observation would fail to start when multiple beans of type `ServerRequestObservationConvention` are defined, which seems like expected behavior to me.

However, with Spring Boot 3.2.0 this behavior changed, and now you don't get a startup failure when multiple `ServerRequestObservationConvention` beans are defined, and silently `DefaultServerRequestObservationConvention` is used.
I think this is unexpected behavior, which should be restored to what it was in Spring Boot 3.1.6.

The tests in the `spring-boot-3.1` and `spring-boot-3.2` branches of this project illustrate this issue. 