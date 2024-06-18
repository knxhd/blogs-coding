<template>
  <div class="hello">
    {{message}}
  </div>
</template>

<script>
import axios from "axios";
import { fetchEventSource } from '@microsoft/fetch-event-source';
export default {
  name: 'HelloWorld',
  data() {
    return {
      message: ''
    }
  },
  mounted() {
    this.invokeStreamApi()
    // this.invokeStreamApi1()
  },
  methods: {
    invokeNoStreamApi() {

    },
    async invokeStreamApi1() {
      const data = {
        "source": "CHAT_GPT",
        "userId": "tianluhua",
        "query": "你好",
        "stream": true,
      }
      const response = await fetch('/llm/application/invokeLlm', {
        method: 'POST',
        headers: {
          'Content-Type': 'text/event-stream'
        },
        body: JSON.stringify(data)
      })
      const reader = response.body.pipeThrough(new TextDecoderStream()).getReader()
      while (true) {
        const { value, done } = await reader.read();
        if (done) {
          this.sseDataSubject.next('done'); // 传一个done的标识
          break;
        }
        this.sseDataSubject.next(value); // 发送消息
        console.log('Received', value);
      }
    },
    invokeStreamApi() {
      const data = {
        "source": "CHAT_GPT",
        "userId": "tianluhua",
        "query": "你好",
        "stream": true,
      }
      const that = this;
      this.message = ''
      fetchEventSource('/llm/application/invokeLlm', {
        method: 'POST',
        headers: {
          'Content-Type': 'text/event-stream',
          'Cache-Control': 'no-cache',
          'Connection': 'keep-alive'
        },
        body: JSON.stringify(data),
        async onopen(response) {
          //建立连接的回调
        },
        async onmessage(message) {
          const data = message.data;
          const dataObject = JSON.parse(data);
          const code = dataObject.code;
          if (code === 200) {
            that.message += dataObject.data;
            console.log(dataObject.data);
          } else if (code === -200) {
            console.log('消息已结束');
          }
        },
        onclose() {
          console.log('关闭');
        },
        onerror(error) {
          console.log(error);
        }
      })
    },
  }
}
</script>

<style scoped>
</style>
