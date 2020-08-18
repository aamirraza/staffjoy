# Staffjoy Teaching Edition
Microservice and cloud native architecture teaching case project, based on Spring Boot and Kubernetes technology stack

## Course Information PPT
1. Chapter 1 [Course Introduction and Case Requirements] (doc/ppts/Chapter_01.pdf)
2. Chapter 2 [System Architecture Design and Technology Stack Selection] (doc/ppts/Chapter_02.pdf)
3. Attached: [Course Reference Material Link](doc/reference.md)

## Original intention of the project

Microservices and cloud-native architectures are the current technical hotspots in the Internet industry. There are many related documents, but there is a lack of end-to-end production-oriented cases. This makes many Internet developers (including architects), although they have learned a lot of microservice theories, But when it comes to implementing the cloud-native microservice architecture, it is still confusing. To this end, I used my spare time to develop this teaching version of the case project by transforming an open source project called [Staffjoy](https://github.com/staffjoy/v2). The entire project adopts a microservice architecture and can be deployed to the Kubernetes container cloud environment with one click. Recently, I cooperated with geeks and developed a course "Spring Boot and Kubernetes Microservice Practice-Comprehensively Master the Architecture Design and Implementation of Cloud Native Applications" based on this case project, refer to [course outline](doc/syllabus.md) . It is hoped that through the learning of actual case projects and courses, developers/architects can not only deeply understand the principles of microservices and cloud native architecture, but also be able to truly implement microservices and cloud native architecture in production practice. I also hope that this project will become a reference template for the cloud native architecture of microservices, and further serve as a scaffolding for similar projects.

## Course targets

In the form of specific cases, teach you how to use the SpringBoot framework to develop a microservice application close to production, and deploy it to the Kubernetes container cloud environment with one click to help you:

1. Master how to design microservice architecture and front-rear separation architecture in practice
2. Master how to build a microservice basic framework based on SpringBoot
3. Master SpringBoot testing technology and related practices
4. Master the relevant practices of service containerization and container cloud deployment
5. Further improve Java/Spring microservice development skills
6. Understand the concept of operational and maintenance architecture and related practices
7. Understand how to structure and design a SaaS multi-tenant application
8. Understand DevOps engineering practices in the cloud era

## Project structure

![arch](doc/images/arch.jpg)

* **Account API (account service)** provides basic functions such as account registration, login authentication and account information management.
* **Company API (company service)**, support the management function of the core concept of team (Team), employee (Worker), task (Job) and shift (Shift).
* **Bot API** is a message forwarding service. On the one hand, it acts as a queue to buffer a large number of notification messages during peak periods, and on the other hand, as a proxy, it can shield possible future notification changes.
* **Mail Sender and SMS Sender**, both of which are message notification services, support email and SMS notification methods respectively. They can be connected to various cloud services, such as Alibaba Cloud email or SMS services.
* **WhoAmI API**, supports front-end applications to obtain detailed information of the currently logged in user, including company and administrator identities, team information, etc. It can also be regarded as a user session information service.
* **App (also known as MyCompany)**, a single-page SPA application, is the main interface of the entire Staffjoy application, through which the company administrator manages the company, employees, tasks, and schedule information.
* **MyAccount**, a single-page SPA application, it mainly supports company employees to manage personal information, including emails and phone calls, etc., to facilitate receiving schedule notification messages.
* **WWW application**, is a front-end MVC application, which mainly supports product marketing, company introduction and user registration and login/logout. This application is also called Marketing Site or Landing Page application.
* **Faraday (Faraday)**, is a reverse proxy (functions similar to nginx), can also be seen as a gateway (functions similar to zuul), it is the traffic entrance for users to access Staffjoy microservice applications, and it is implemented Routing access to front-end applications and back-end APIs also implements security functions such as login authentication and access control. Faraday proxy is the key to Staffjoy's microservice architecture and front-rear separation architecture, and it is the only service with public IP.

The communication between Staffjoy microservices, including the external exposure API, all adopt the JSON over HTTP standard method.

![skywalking](doc/images/skywalking.png)

The above picture is the service dependency graph displayed in real time on the Skywalking Dashboard after the call chain buried point monitoring. This dependency graph is consistent with the overall architecture design.

## Project Technology Stack

* Spring Boot
* Spring REST
* Spring Data JPA
* Spring MVC + Thymeleaf
* MySql
* ReactJs + Redux
* Docker Compose
* Kubernetes

The technology stacks used in the Staffjoy Education Edition are all mainstream in the current industry, and the number is small, as indicated in the architecture diagram above. All microservices (marked in green) are developed using **Spring REST**, those with data access interaction use **Spring Data JPA**, and the database uses **MySQL**. WWW service is developed using **Spring MVC+Thymeleaf** template engine. Faraday is also a **SpringBoot** application, with internal routing and security logic based on **Servlet Filter**. Both single-page SPA applications (marked in dark red) are developed using the **ReactJs+Redux** framework. The entire application supports one-click deployment to the local **Docker Compose** environment, and also supports one-click deployment to the **Kubernetes** container cloud environment, so the overall architecture of Staffjoy supports cloud-native microservice architecture.

## Further explanation about the project

1. The teaching version of Staffjoy and the original version of Staffjoy are basically the same in function, design and implementation logic, but the teaching version has made some modifications on the basis of the original version to meet the needs of teaching. First, in the development language framework, the original Staffjoy uses Golang/Grpc to implement microservices, while the teaching version of Staffjoy is transformed to use the more mainstream Spring (Boot) to implement microservices in China; secondly, in terms of architecture, the original Staffjoy uses Grpc to develop microservices. Service, in order to expose Rpc service as HTTP/REST service, it has a corresponding Grpc API Gateway conversion layer service, and the teaching version of Staffjoy uses Spring (Boot) development and directly supports HTTP/REST interface, so there is no need for a separate conversion layer Service; third, the original version of Staffjoy uses SMS to send scheduling notification information by default, but the SMS service in China requires approval, which is more troublesome. Therefore, in the teaching version of Staffjoy, the scheduling notification is adjusted to email by default, which is convenient for testing and demonstration. After learning and understanding Spring (Boot) teaching version of Staffjoy, it is easy to understand the original version of Staffjoy developed by Golang/Grpc. Students who are interested in the original version can directly look at the official [source code](https://github.com/Staffjoy/v2).
2. To develop and run the teaching version of Staffjoy, you need to install some necessary development tools (the operating system is not limited), including JDK8, Maven dependency management tool, Intellij IDEA or Eclipse STS IDE, MySQL database and MySQL Workbench management tool, Nodejs/npm front-end Development framework, Postman API testing tool, and Docker runtime environment. Because there are many Staffjoy services, if you want to run on this machine, it is recommended that the physical memory ** not less than 16G**.
3. Although the teaching version of Staffjoy is a relatively complete SaaS application, and a lot of production links are considered in the architecture design, it is still only a teaching demonstration project for learning reference only. If you want to use it for production ( Or scaffolding other projects based on its code), it still needs to be rigorously tested and customized extensions. In the process of learning or using the teaching version of Staffjoy, if you find a bug or have suggestions for improving the project, please submit a github issue.

## How to run

1. Configuration file

Staffjoy Education Edition relies on some private configurations, such as sentry-dsn and aliyun-access-key, etc. These private configurations cannot be checked in to github, so a private configuration mechanism of Spring is adopted. The private data is configured in **config/ In application.yml**, this file is in gitignore and will not be checked in to github. Please refer to the [application.yml.example](config/application.yml.example) file and format in the config directory, add an **appliction.yml** file in the config directory, and fill in your own private configuration. If you don't have these configurations for the time being, you can temporarily use fake data and directly change application.yml.example to application.yml so that the application can run. Note that if the aliyun-related configuration is not matched, you cannot send emails or text messages. If the sentry-related configuration is not matched, you cannot send abnormal data to sentry. If intercom is not matched, you cannot connect to the intercom customer service system. Recaptcha may not be matched if it is not used.

TODO

## Staffjoy company and case background
[Staffjoy](https://www.staffjoy.com/) was a start-up company in Silicon Valley, USA, founded in 2015, the founder is [Philip I. Thomas](https://www.linkedin.com/in /philipithomas/), the company has received investment from well-known institutions such as Y Combinator. Staffjoy's main business is to provide small companies with software solutions for scheduling (Scheduling) to help companies improve employee management efficiency, mainly for retail, catering and other service industries. Due to business development and recruitment and other reasons, [Staffjoy was finally closed in 2017](https://blog.staffjoy.com/denouement-abe7d26f2de0). Before closing, the company put most of its core products [open source](https: //github.com/Staffjoy) contributed to the Github community. [Staffjoy V2](https://github.com/Staffjoy/v2) is the latest SaaS version of the enterprise scheduling system developed before the company closed. Currently, it has more than 1k stars on Github, and the overall design and code quality is high. At present, many companies are customizing. Staffjoy V2 is a small-scale SaaS application that uses microservices and a front-to-back separation architecture to support one-click deployment of Kubernetes/GKE container cloud environments. It is a template project for learning modern SaaS, microservices and cloud native architecture.

## Functional requirements of Staffjoy application

The business functions of the Staffjoy application are relatively simple. Simply put, it helps small business managers manage employees and schedules, and notify employees of schedule information in a timely manner by means of text messages or emails. Specifically, Staffjoy mainly supports two types of user roles and use cases. One is the company administrator (admin), who can manage the company (company), employee directory (directory), team (team) and employee (worker) through Staffjoy, and also You can create jobs, create and publish shift information; the other is company employees, who can manage personal information such as phone calls and emails through Staffjoy, so that they can receive corresponding shift notifications. The Staffjoy application is mainly provided in the form of a shared SaaS service, and it also supports customized private deployment for some large customers. This requires that the Staffjoy application is easy to deploy and maintain, and supports one-click deployment to container cloud environments such as GKE. In addition, as a SaaS service product, good marketing and customer service are the keys to winning users, so Staffjoy needs to provide marketing friendly promotion and landing pages, as well as support for mainstreaming Online customer service systems such as Intercom.

## Project interface preview

### 1. Home

![landing page](doc/images/landing_page.jpg)

### 2. Order plan and price page

![plan and price](doc/images/plan_and_price.jpg)

### 3. Login page

![login page](doc/images/login_page.jpg)

### 4. Employee Account Management SPA Single Page Application

![account edit page](doc/images/account_edit_page.jpg)

### 5. My company SPA single page application

![scheduling page](doc/images/scheduling_page.jpg)


## Other reference microservice case projects

* [eShopOnContainers](https://github.com/dotnet-architecture/eShopOnContainers) Microsoft support
* [microservices-demo](https://github.com/GoogleCloudPlatform/microservices-demo) Google support
* [piggy-metrics](https://github.com/sqshq/piggymetrics)
