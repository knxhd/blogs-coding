## 服务端

1. websocket和event-stream的优缺点

> WebSocket和Event-Stream（Server-Sent Events）都是实现实时通信的技术，但是它们各自有不同的优缺点。

⭐️ WebSocket

> - 优点:
>
> 1. 双向通信：WebSocket提供了一个全双工的通信通道，客户端和服务器可以同时发送和接收数据。
> 2. 实时性：由于WebSocket是持久连接，所以它具有高实时性。
> 3. 更少的数据传输量：WebSocket在建立连接后，数据传输时不需要包含HTTP头，因此数据传输量较小。
> 4. websocket可传输较为复杂的数据结构，例如json、二进制字节等。
> 5. websocket针对java、Python等语言支持较好
> 6. websocket天然支持跨域问题(据说也有跨域问题，目前无法模拟出来)
>
> - 缺点:
>
> 1. 虽然大部分现代浏览器都支持WebSocket，但是一些老版本的浏览器可能不支持。
> 2. 协议复杂：WebSocket的协议相对复杂，需要处理连接、断开连接、心跳等问题。
>
> - 适用场景
>
> 1. 适合较为复杂的业务场景，需要多次进行通讯，例如：聊天、游戏等

🌟 Event-Stream (Server-Sent Events):

> - 优点:
>
> 1. 简单易用：Event-Stream的API相对简单，易于使用和理解。
> 2. 自动重连：如果连接断开，Event-Stream会自动尝试重连。
> 3. 基于HTTP：Event-Stream基于HTTP协议，因此可以利用现有的HTTP基础设施，如负载均衡和缓存。
>
> - 缺点:
>
> 1. 单向通信：Event-Stream只支持服务器向客户端发送数据，不支持客户端向服务器发送数据。
> 2. 实时性：由于Event-Stream是基于HTTP的，因此它的实时性可能不如WebSocket。
> 3. 数据传输量：Event-Stream在每次发送数据时都需要包含HTTP头，因此数据传输量可能较大。
> 4. 数据结构：Event-Stream传输仅仅支持字符形式，无法适应较复杂的场景
> 5. java流式下发可能出现丢包、站包问题，需要自己实现编解码来对消息进一步处理
>
> - 适用场景
>
> 1. 适合较为简单的场景，例如：大模型客户端发一次消息后，服务端返回结果

### event-stream形式

#### JAVA

> - java实现SSE的方式主要有2种，即通过Spring mvc提供的SseEmitter和Flux。目前我用的比较多的是SseEmitter(原因：本人比较懒，此方式较为简单)
> - 只需定义一个 `SseEmitter`对象并输出即可，通过 `SseEmitter.send()`发送消息(发送消息需要异步执行)
> - SseEmitter构造函数中，超时时间设置为0，否则请求过长，会出现超时情况(默认超时时间和http接口相同，可通过spring配置进行设置)

> - SseEmitter种主要的方法
>
> 1. send：发送消息
> 2. complete()：表示消息已结束
> 3. completeWithError(): 发送错误并结束，多用于异常捕获中

```java
   @GetMapping(value = "sseEmitterTest")
    public Object sseEmitterTest(@RequestParam(value = "name") String name) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 50, 60L, TimeUnit.SECONDS, queue);
        threadPoolExecutor.execute(() -> {
            char[] charArray = name.toCharArray();
            for (char charStr : charArray) {
                try {
                    sseEmitter.send(new String(new char[]{charStr}));
                    Thread.sleep(100);
                } catch (IOException | InterruptedException e) {
                    sseEmitter.completeWithError(e);
                }
            }
            sseEmitter.complete();
        });

        return sseEmitter;
    }
```

## 客户端

### JAVA

#### 引入依赖

```xml
 <dependency>
    <groupId>org.asynchttpclient</groupId>
    <artifactId>async-http-client</artifactId>
 	<version>2.12.3</version>
 </dependency>

<dependency>
   <groupId>org.projectlombok</groupId>
   <artifactId>lombok</artifactId>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>3.5.2</version>
</dependency>
```

#### 调用接口

##### 消息处理类

```java
public class DefaultMessageHandler implements AsyncHandler<Response> {

    @Override
    public State onStatusReceived(HttpResponseStatus httpResponseStatus) {
        return null;
    }


    @Override
    public State onHeadersReceived(HttpHeaders httpHeaders) {
        return null;
    }

    @Override
    public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) {
 		// 业务逻辑处理
        return State.CONTINUE;
    }

    @Override
    public void onThrowable(Throwable throwable) {

    }

    @Override
    public Response onCompleted() {
        return null;
    }

}
```

##### 主方法

```java
@NoArgsConstructor
public class HttpStreamClient {

    private AsyncHandler<Response> asyncHandler;

    public HttpStreamClient(AsyncHandler<Response> asyncHandler) {
        this.asyncHandler = asyncHandler;
    }


    public void sendMessage(String url, String message) {
        // 组装okhttp请求
        AsyncHttpClient client = Dsl.asyncHttpClient();
        BoundRequestBuilder requestBuilder = client.preparePost(url);
        requestBuilder.setHeader("Accept", "text/event-stream");
        requestBuilder.setHeader("Content-Type", "application/json");
        requestBuilder.setBody(message);
        requestBuilder.execute(asyncHandler);
    }

    public static void main(String[] args) {
        DefaultMessageHandler defaultMessageHandler = new DefaultMessageHandler();
        HttpStreamClient httpStreamClient = new HttpStreamClient(defaultMessageHandler);
        String message = "Hello WOrld!"
        httpStreamClient.sendMessage("API", message);
    }
}
```

### Python

#### 引入依赖

```python
pip install requests
```

#### 编码部分

```python
import requests
import json

def invokeStreamLlm():
    requestBody = {
    	name: "Hello World!"
    }
    response = requests.post(
        "API",
        json=requestBody,
        stream=True,
        headers={"Content-Type": "application/json"},
    )
    if response.status_code != 200:
        return
    for line in response.iter_lines():
        if line and line.strip():
            content = json.loads(line[5:])
            code = content['code']
            if code == 200:
                print(content['data'])
            elif code == -200:
                print('结束标识')


if __name__ == "__main__":
    content = invokeStreamLlm()
```

### vue

> - 本地开发时，通过vue的正向代理，流式接口无法做到流式下发。数据会在最后一块全部下发
> - 本地想要流式下发解决方法：
> - 本地部署一个nginx，通过nginx反向代理接口后，可实现流式下发
> - 注：实际项目中，url则是vue配置的api，ip和端口采用代理来代替

#### 引入依赖

```shell
npm install @microsoft/fetch-event-source -D
```

#### 编码部分

> - penWhenHidden表示是否监听窗口变化，为true表示不见听窗口变化，集焦点离开窗口时，不自动断开重连
> - ctrl:
>   - 由于sse接口存在断开重连机制，可能存在多次 调用的情况
>   - 通过ctrl控制器，可以控制关闭客户端，不会再进行重连

```vue
 	<script>
import { fetchEventSource } from '@microsoft/fetch-event-source';
export default {
  data() {
    return {
        ctrl: new AbortController(),
    };
  },
  mounted() {
    this.invokeSse();
  },
  methods: {
    invokeSse() {
      const that = this;
      fetchEventSource('API', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Accept: ['text/event-stream', 'application/json'],
        },
        body: JSON.stringify({
             data: ''
        }),
	penWhenHidden: true,
        signal: that.ctrl.signal,
        onopen(event) {
          console.log(event);
        },
        onmessage(msg) {
          // 消息接收
          console.log(msg);
        },
        onclose(e) {
          console.log(e);
	  that.ctrl.abort();
        },
        onerror(err) {
          console.log(err);
	  that.ctrl.abort();
        },
      });
    }
  },
};
</script>
```
