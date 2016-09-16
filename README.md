# Pet Clinic by Spring Boot in Java v1.1.0

------

## Goal
Implement the Spring 4 Sample Pet Clinic project for practice.

## Source
1. GitHub: https://github.com/puncha/java-petclinic
2. Travis CI: [![Build Status](https://travis-ci.org/puncha/java-petclinic.svg)](https://travis-ci.org/puncha/java-petclinic)
3. CodeShip CI: [![Build Status](https://codeship.com/projects/4b274a90-45f7-0134-2d77-0aad117e5610/status)](https://codeship.com/projects/168779)
4. Heroku Demo: https://java-petclinic.herokuapp.com/

## Features
It contains three versions:
 - SpringMVC + Jsp (backend rendering)
 - SpringMVC Restful + AngularJs1 + Bootstrap4
 - SpringMVC Restful + Angular2 + Bootstrap4 (TypeScript)

## Install
```bash
cd src/main/webapp/resources/ng2
npm i
```

## Run
```bash
./gradlew bootRun
```

## Test
```bash
./gradlew check
```

## Cross platform desktop app
Install NodeJs 6.x first and run:
```bash
cd src/main/electron_app/
npm i
node_modules/.bin/electron .
```

## References
- Pet Clinic by Spring Roo in Java
  - GitHub: https://github.com/spring-projects/spring-roo
  - Heroku Demo: http://petclinic.herokuapp.com
  - Official Site: http://projects.spring.io/spring-roo/

## Change Logs
 - v1.1.0:
  - Angular2 version is added.
 - v1.0.0:
  - CSS is changed to Bootstrap 4 alpha3.
  - Form validation is added.
  - Site of JSP version is implemented. 
  - Site of AngularJs1 + Restful Controller is implemented.
  - Prototype of Electron App is ready for try.
 - v0.4.0:
  - Pet page is implemented. Users can list, create, edit and delete pets.
  - Known issue: pet with visit events fail to be deleted.
 - v0.3.0:
  - Owner page is implemented. Users can list, create, edit and delete owners.
 - v0.2.0:
  - Error page is implemented. User can see how the site handles exceptions.
 - v0.1.0:
  - Index page is implemented. User can see the home page of the site.
