# Java API Client for Streamable.com

## Basic Usage
#### Building
```shell
$ gradle build
```
#### Running
```shell
$ java -jar build/libs/uploadable-apiclient-java-0.1.jar
```


#### Build a client
```java
// Streamable.com username and password
Uploadable client = new Uploadable.Builder()
    .setCredentials("<username>", "<password>")
    .build();
```
#### Upload a video
```java
File file = new File("/path/to/file.mp4");
UploadFile uploadFile = new UploadFile("video/mp4", file);

// 2nd arg is hashmap of optional params like "mute" and "noresize"
Result result = client.uploadVideo(uploadFile, null);
String shortCode = result.getShortCode();

Status status = client.checkStatus(shortCode);
while (status.getStatusCode() != Status.COMPLETED) {
    // Just sleep or something
    Thread.sleep(1000);
    status = client.checkStatus(shortCode);
}

System.out.println(status.getUrlRoot());
// //cdn2.streamable.com/video/mp4/abcd

```

#### Import a video from a URL
```java
URL importUrl = new URL("http://example.com/video.mp4");

// 2nd arg is hashmap of optional params like "mute" and "noresize"
Result result = client.importVideo(importUrl, null);
String shortCode = result.getShortCode();

Status status = client.checkStatus(shortCode);
while (status.getStatusCode() != Status.COMPLETED) {
    // Just sleep or something
    Thread.sleep(1000);
    status = client.checkStatus(shortCode);
}

System.out.println(status.getUrlRoot());
// //cdn2.streamable.com/video/mp4/abcd

```
