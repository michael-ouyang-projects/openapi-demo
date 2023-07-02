# openapi-demo
Describe how to generate REST API interfaces using OpenAPI specifications.

---

## 1. Create a new Spring Project using Spring Initializr
Provide the configuration in the below image.
<img src="images/spring-initializr-config.png">

## 2. Create "OpenAPI specifications" in /src/main/resources/openapi
<img src="images/openapi-specifications.png" width=400 alt="openapi-specifications">

---
## 3. Update build.gradle
### 3-1. Adjust the dependencies block to make it more clear and readable
```groovy
dependencies {
    // spring
    implementation 'org.springframework.boot:spring-boot-starter-web'

    // openapi
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'
    implementation 'org.openapitools:jackson-databind-nullable:0.2.6'

    // lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // test
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

### 3-2. Add [openapi java generator plugin](https://openapi-generator.tech/) to plugin block
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.1.1'
    id 'io.spring.dependency-management' version '1.1.0'
    id "org.openapi.generator" version "6.6.0"
}
```

### 3-3. Set the [configuration parameters]() for the graphqlCodegen task provided by the plugin
Parameters contain the location of graphql schemas, output directory, and the package name for the generated interface and model.
If you just have one openapi specification, you can use the openApiGenerate task provided by the plugin
```groovy
openApiGenerate {
	generatorName = 'spring'
	inputSpec = file("${projectDir}/src/main/resources/openapi/user.yaml").toString()
	outputDir = file("${buildDir}/generated/openapi").toString()
	apiPackage = 'fun.mouyang.interfaces.rest.controller'
	modelPackage = 'fun.mouyang.interfaces.rest.dto'
	configOptions = [
		useSpringBoot3: "true",
		interfaceOnly: "true"
	]
}
```

### 3-4. Add the directory that contains the generated interface and model to the project source sets
```groovy
sourceSets {
    main {
        java {
            srcDir 'src/main/java'
            srcDir "${buildDir}/generated/openapi/src/main/java"
        }
    }
}
```

### 3-5. Execute the openApiGenerate task during gradle build process
```groovy
compileJava.dependsOn tasks.openApiGenerate
```

---

## 4. Create [controllers](src/main/java/fun/mouyang/interfaces/graphql/controller) in fun.mouyang.interfaces.openapi.controller
<img src="images/graphql-controller.png" width=400 alt="graphql-controller">

---

## 5. Enable graphiql for interactively exploring the GraphQL API
You can open the browser and navigate to [localhost](http://localhost:8080/graphiql?path=/graphql) to execute some requests after starting the application.
```properties
# GraphQL
spring.graphql.graphiql.enabled = true
spring.graphql.graphiql.path = /graphiql
spring.graphql.schema.locations = classpath:graphql
```
<img src="images/graphiql.png">