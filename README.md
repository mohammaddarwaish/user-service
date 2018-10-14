# User-Service
Provide User CRUD, authorisation and authentication operations

The diagram describe the Project structure
~~~
|   .gitignore
|   banner.txt
|   build.gradle
|   gradlew
|   gradlew.bat
|   LICENSE
|   NOTICE
|   README.md
|   settings.gradle
|
+---config
|   +---checkstyle              // Checkstyle configurations
|   \---findbugs                // Findbugs filters configurations
+---gradle
|   \---wrapper
\---src
    +---main
    |   +---java
    |   |   \---com
    |   |       \---github
    |   |           \---userservice
    |   |               +---config
    |   |               +---controllers
    |   |               +---data
    |   |               |   +---models
    |   |               |   \---repositories
    |   |               +---exceptions
    |   |               +---services
    |   |               \---views
    |   \---resources
    |       \---db
    |           \---changelog       // Liquibase change logs
    \---test
        +---java
        |   \---com
        |       \---github
        |           \---userservice
        |               +---controllers
        |               +---data
        |               |   \---repositories
        |               +---helpers
        |               +---integration
        |               \---services
        \---resources
~~~

### How do I get set up?

A Spring-boot java application. The project uses Gradle wrapper and doesn't require any extra dependencies except for Java JDK 8 or greater.

To execute any of the below setups please go to the root directory of the project. 
Depending on the operating system you may not need to prepend `./` before each Gradle command

The project uses Lombok. For development in Intellij, install the Lombok plugin and enable the following preference:
 `Build, Execution, Deployment > Compiler > Annotation Processors > â€œEnable annotation processing"`. (per project basis) 

Also enable Optimize imports on the fly: `Settings > Editor > General > Auto Import > "Optimize imports on the fly"`.

#### Run tests

To run the tests execute `./gradlew test`

#### build application

To build the project execute `./gradlew build`

Note: this will run all static code analysers such as CheckStyle, Findbugs, and PMD and run tests before 
building the project.      
To only build the project without running any checks execute `./gradlew assemble`

Both of these steps will create an executable jar in: _${ProjectDir}\build\libs

##### Run application locally #####

To run the application without building execute `./gradlew bootRun`
It will build the project and start the application. By default the application will use the `dev` profile,
to use a different profile execute the following command `./gradlew bootRun -Dspring.profiles.active=NAME_OF_PROFILE`

You can also pass configuration such as database configuration or any other configuration. For example to run
the application on port 80 rather than the default 8080 execute `./gradlew bootRun -Dserver.port=80`

You can also provide a file that contains the configuration `./gradlew bootRun -Dspring.config.location=<file-path>\application.yaml`

Go to the URL `http://localhost:8080/swagger-ui.html` for swagger UI documentation.

##### build Docker image locally #####

The project contains a Dockerfile that has all necessary instruction to build an image
Execute `docker build -t user-service .` to create an image.

To run the image execute `docker run -p :8080:8080 user-service`

##### Checks and reports #####

To run all checks such as tests, Checkstyle, Findbugs and PMD execute `./gradlew check` .
The reports produced can be found in: ${ProjectDir}\build\reports

You can also execute a single check such as `./gradlew checkStyleMain` will only run checkstyle on the /src/main directory
