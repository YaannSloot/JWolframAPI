# JWolframAPI
[![](https://jitpack.io/v/YaannSloot/JWolframAPI.svg)](https://jitpack.io/#YaannSloot/JWolframAPI/1.0)
[![](https://img.shields.io/badge/javadoc-1.0.0-blue)](https://javadoc.jitpack.io/com/github/YaannSloot/JWolframAPI/1.0.0/javadoc)  
  
A simple Java binding for the Wolfram|Alpha API

## Usage

First create a WolframClient object
```
try {
  WolframClient client = new WolframClient("DEMO");
} catch (InvalidAppidException e) {
  // This happens if your app id is invalid
  e.printStackTrace();
}
```
Then perform a query using the following methods:
* **completeQuery**
* **queueQuery**
* **submitQuery**
