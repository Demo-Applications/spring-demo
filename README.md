# spring-demo
Spring powered microservices

## In Progress

<img src="https://geps.dev/progress/30?dangerColor=006600&warningColor=006600&successColor=006600" style="zoom:140%;" />

[Toc]



## Setup

#### Build Applications

The project is a multi-module maven project with these components :

* Commons
* API-Gateway [Not Implemented Yet]
* Service1

The parent project configures the plugins and binding it's goal to a maven lifecycle phase, build order, execution etc.

From the parent pom directory, run :

```
mvn clean package
```

to build all the modules in configured order.

#### External Services

Follow this [Readme](external services/Readme.md) to setup and start the external services.

