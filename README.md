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
|   +---checkstyle
|   \---findbugs
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
    |           \---changelog
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

Both of these steps will create an executable jar in: _${ProjectDir}\build\libs_