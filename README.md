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
  implementation 'com.github.YaannSloot:JWolframAPI:Tag'
}
```
### Usage
**Synchronous blocking query example**
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
This will query for the answer to "2+2" and print out the response. This example also blocks the current thread to complete the query, so you may experience a noticeable delay while the api endpoint streams the results back to the client.
