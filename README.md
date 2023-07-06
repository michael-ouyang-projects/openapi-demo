# openapi-demo
Describe how to generate REST API interfaces using OpenAPI specifications.

---

## 1. Create a new Spring Project using Spring Initializr
Provide the configuration in the below image.
<img src="images/spring-initializr-config.png">

## 2. Create "OpenAPI specifications" in /src/main/resources/static/openapi
<img src="images/openapi-specifications.png" width=400 alt="openapi-specifications">

---
## 3. Update build.gradle
### 3-1-1. Add [springdoc openapi starter dependency](https://springdoc.org/) for creating swagger ui
### 3-1-2. Adjust the dependencies block to make it more clear and readable
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

### 3-2. Add [openapi java generator plugin](https://openapi-generator.tech/) to plugin block for code generation
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.1.1'
    id 'io.spring.dependency-management' version '1.1.0'
    id "org.openapi.generator" version "6.6.0"
}
```

### 3-3. Set the [configuration parameters](https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-gradle-plugin/README.adoc) for the openApiGenerate task provided by the plugin
Parameters contain the generator, location of the openapi specs, output directory, and the package name for the generated interfaces and models.
```groovy
openApiGenerate {
	generatorName = 'spring'
	inputSpec = file("${projectDir}/src/main/resources/static/openapi/user.yaml").toString()
	outputDir = file("${buildDir}/generated/openapi").toString()
	apiPackage = 'fun.mouyang.interfaces.rest.controller'
	modelPackage = 'fun.mouyang.interfaces.rest.dto'
	configOptions = [
		useSpringBoot3: "true",
		interfaceOnly: "true"
	]
}
```

### 3-4. Add the directory that contains the generated interfaces and models to the project source sets
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

## 4. Create [UserController](src/main/java/fun/mouyang/interfaces/rest/controller/UserController.java) in fun.mouyang.interfaces.rest.controller
<img src="images/rest-controllers.png" width=400 alt="rest-controllers">

---

## 5. swagger-ui is enable by default if "springdoc-openapi-starter" is in the dependencies block
You can open the browser and navigate to [localhost](http://localhost:8080/swagger-ui/index.html) to execute some requests after starting the application.
<img src="images/swagger-ui.png">