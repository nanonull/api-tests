API Load tests using https://gatling.io/

# Run simulations

## Using gatling gradle plugin

[gradle-gatling-plugin](https://github.com/lkishalmi/gradle-gatling-plugin)

Example command to run all simulations (please specify API_TOKEN only):

API_TOKEN=<api-token-of-demo-rails-app> BASE_URL=https://demoapp.strongqa.com INCLUDE_SIMULATION_PATTERNS=\*\*/\*\*Simulation** ./gradlew gatlingRun

`INCLUDE_SIMULATION_PATTERNS` may have multiple patterns to match packages and classes split by comma. [PatternFilterable doc](https://docs.gradle.org/current/javadoc/org/gradle/api/tasks/util/PatternFilterable.html)
