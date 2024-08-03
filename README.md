# Fray Gradle Plugin

This plugin helps you to use Fray in your Gradle project.

```kotlin   
plugins {
    id("cmu.pasta.fray.gradle") version "1.0"
}
```

Now you can write unit tests and run them with Fray.

```java

@FrayTest
class MyTest {
    @Analyze 
    public void test() {
        // Your test code here
    }
}
```

To run all Fray tests, use the following command:

```shell
./gradlew frayTest
```