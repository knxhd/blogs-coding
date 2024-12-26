# JAVA调用方式

## JAVA原生实现

```java
package com.hisense.demo.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author tianluhua
 * @version 1.0
 * @since 2024/11/13 17:49
 */
public class Demo {
    public static void main(String[] args) {
        String targetURL = "http://10.18.217.60:32641/film_grain_analysis"; // 替换为目标URL
        File[] files = {
                new File("D:\\desktop\\sample_img.jpg"), // 替换为第一个文件路径
                new File("D:\\desktop\\sample_img_1.jpg")  // 替换为第二个文件路径
        };
        String deviceId = "wfureufeewe"; // 替换为实际的deviceid

        try {
            uploadFiles(targetURL, files, deviceId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void uploadFiles(String targetURL, File[] files, String deviceId) throws IOException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        String LINE_FEED = "\r\n";

        URL url = new URL(targetURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        // 请求超时时间
        httpConn.setReadTimeout(60 * 1000);
        // 连接超时时间
        httpConn.setConnectTimeout(10 * 1000);
        httpConn.setDoInput(true);
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (DataOutputStream request = new DataOutputStream(httpConn.getOutputStream())) {
            // Add deviceid part
            request.writeBytes("--" + boundary + LINE_FEED);
            request.writeBytes("Content-Disposition: form-data; name=\"deviceid\"" + LINE_FEED);
            request.writeBytes(LINE_FEED);
            request.writeBytes(deviceId + LINE_FEED);

            // Add file parts
            for (File file : files) {
                String fileName = file.getName();
                request.writeBytes("--" + boundary + LINE_FEED);
                request.writeBytes("Content-Disposition: form-data; name=\"files\"; filename=\"" + fileName + "\"" + LINE_FEED);
                request.writeBytes("Content-Type: " + HttpURLConnection.guessContentTypeFromName(fileName) + LINE_FEED);
                request.writeBytes(LINE_FEED);

                try (FileInputStream inputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        request.write(buffer, 0, bytesRead);
                    }
                }
                request.writeBytes(LINE_FEED);
            }

            // End of multipart/form-data.
            request.writeBytes("--" + boundary + "--" + LINE_FEED);
            request.flush();
        }

        // Check server's response
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder response = new StringBuilder();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            String responseStr = response.toString();
            System.out.println(responseStr);
        } else {
            System.out.println("File upload failed. Server returned HTTP code: " + responseCode);
        }
    }

}

```

## OKHttp实现

```java
package com.hisense.demo.mobileagent;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author tianluhua
 * @version 1.0
 * @since 2024/7/25 14:33
 */
public class Demo {

   /**
    * 对应的 POST 请求接口，采用 form-data传参
    * 
    */
    public static void main(String[] args) throws IOException {

        // 定义的接口API
        String api = ""
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
  
        // 请求参数，可能有多个
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                // 文件参数，参数名为file，根据具体的接口协议来确定，文件的路径根据实际来确定
                .addFormDataPart("file", "img.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"), new File("E:\\work\\common\\coding\\java-demo\\src\\main\\java\\com\\hisense\\demo\\mobileagent\\img.png")))
                // 其他参数
                .addFormDataPart("instruction","hello")
                // 其他参数
                .addFormDataPart("knowledge","hello")
                .build();
        Request request = new Request.Builder()
                .url(api)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();

        // 返回响应结果
        System.out.println(response);
        // 返回结果数据
        String json = response.body().string();
    }
}

```

## Openfeign实现

- fallbackFactory为反馈机制，也可以为空，采用默认的方式

```java
@Component
@FeignClient(value = "test", contextId = "test", url = "${test.api}", fallbackFactory = TestApiFallbackFactory.class)
public interface TestApi {

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String generate(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "param") String param);

}
```

# Python调用方式

## Requests库

```python
import requests

url = "http://10.18.217.60:32641/film_grain_analysis"

payload = {'deviceid': '111212111'}
files=[('files',('sample_img.jpg',open('D:\\desktop\\sample_img.jpg','rb'),'image/jpeg')),
  ('files',('sample_img.jpg',open('D:\\desktop\\sample_img.jpg','rb'),'image/jpeg'))
]
headers = {}

response = requests.request("POST", url, headers=headers, data=payload, files=files)

print(response.text)

```
