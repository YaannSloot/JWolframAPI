# JWolframAPI
[![](https://jitpack.io/v/YaannSloot/JWolframAPI.svg)](https://jitpack.io/#YaannSloot/JWolframAPI)
[![](https://img.shields.io/badge/javadoc-1.0.1-blue)](https://javadoc.jitpack.io/com/github/YaannSloot/JWolframAPI/1.0.1/javadoc)
[![](https://img.shields.io/badge/Wolfram%7CAlpha%20API-home%20page-orange)](http://products.wolframalpha.com/api/)
[![](https://img.shields.io/badge/Wolfram%7CAlpha%20API-documentation-orange)](http://products.wolframalpha.com/docs/WolframAlpha-API-Reference.pdf)  
  
A simple Java binding for the Wolfram|Alpha API

## Getting started

### Add the library to your project
**Maven**  
Add the repository
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
And the dependency
```xml
<dependency>
  <groupId>com.github.YaannSloot</groupId>
  <artifactId>JWolframAPI</artifactId>
  <version>VERSION</version>
</dependency>
```
**Gradle**  
Add the repository to build.gradle
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
And the dependency
```gradle
dependencies {
  implementation 'com.github.YaannSloot:JWolframAPI:VERSION'
}
```
### Usage
**Synchronous blocking query example**:
```java
WolframClient client = new WolframClient("DEMO");
QueryResult result = client.completeQuery("2+2");
if (result.wasSuccess()) {
  for (Pod p : result.getPods()) {
    System.out.println(p.getTitle());
    for (Subpod sp : p.getSubpods()) {
      System.out.println(sp.getPlaintext());
    }
  }
}
```
This will query for the answer to "2+2" and print out the response. This example uses the **completeQuery()** method, which will block the current thread to complete the query. Query requests can take time to complete, so you may experience a noticeable delay until communication with the api endpoint has finished.  
  
**Asynchronous example using [consumers](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html)**:
```java
WolframClient client = new WolframClient("DEMO");
client.queueQuery("2+2", result -> {
  if (result.wasSuccess()) {
    for (Pod p : result.getPods()) {
      System.out.println(p.getTitle());
      for (Subpod sp : p.getSubpods()) {
        System.out.println(sp.getPlaintext());
      }
    }
  }
});
```
This will query for the answer to "2+2" and print out the response, however the entire request is done asynchronously. Once the task completes, this method will fire the consumer that in this case was passed as a lambda function.  
  
**Asynchronous example using [future](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html)**:
```java
WolframClient client = new WolframClient("DEMO");
Future<QueryResult> futureResult = client.submitQuery("2+2");
// Somewhere later...
try {
  QueryResult result;
  result = futureResult.get();
  if (result.wasSuccess()) {
    for (Pod p : result.getPods()) {
      System.out.println(p.getTitle());
      for (Subpod sp : p.getSubpods()) {
        System.out.println(sp.getPlaintext());
      }
    }
  }
} catch (InterruptedException | ExecutionException e) {
  e.printStackTrace();
}
```
This will query for the answer to "2+2" and print out the response, however the entire request is done asynchronously. This example in particular uses a future to represent the query task. Ideally you should use the get() method once you need to collect the results, otherwise this would function in the same was as the first example. The query task can also be canceled at any time, just the same as any future object.
