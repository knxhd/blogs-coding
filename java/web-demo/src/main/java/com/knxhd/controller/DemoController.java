package com.knxhd.controller;

import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author tianluhua
 * @version 1.0
 * @since 2024/6/5 9:41
 */
@RestController
@RequestMapping(value = "")
public class DemoController {


    @GetMapping(value = "testSse")
    public SseEmitter testSseGet(HttpServletResponse response, Map<String, String> data) throws IOException, InterruptedException {
        SseEmitter sseEmitter = new SseEmitter();

        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        System.out.println(data);
        String text = "《春江花月夜》是唐代诗人张若虚的作品，这首诗是中国古典诗歌中不可多得的佳作，被誉为“孤篇盖全唐”的杰作。全诗如下：\n" +
                "\n" +
                "春江潮水连海平，海上明月共潮生。\n" +
                "滟滟随波千万里，何处春江无月明！\n" +
                "\n" +
                "江流宛转绕芳甸，月照花林皆似霰。\n" +
                "空里流霜不觉飞，汀上白沙看不见。\n" +
                "\n" +
                "江天一色无纤尘，皎皎空中孤月轮。\n" +
                "江畔何人初见月？江月何年初照人？\n" +
                "\n" +
                "人生代代无穷已，江月年年望相似。\n" +
                "不知江月待何人，但见长江送流水。\n" +
                "\n" +
                "白云一片去悠悠，青枫浦上不胜愁。\n" +
                "谁家今夜扁舟子？何处相思明月楼？\n" +
                "\n" +
                "可怜楼上月徘徊，应照离人妆镜台。\n" +
                "玉户帘中卷不去，捣衣砧上拂还来。\n" +
                "\n" +
                "此时相望不相闻，愿逐月华流照君。\n" +
                "鸿雁长飞光不度，鱼龙潜跃水成文。\n" +
                "\n" +
                "昨夜闲潭梦落花，可怜春半不还家。\n" +
                "江水流春去欲尽，江潭落月复西斜。\n" +
                "\n" +
                "斜月沉沉藏海雾，碣石潇湘无限路。\n" +
                "不知乘月几人归，落月摇情满江树。\n" +
                "\n" +
                "这首诗描绘了春江、花月、江水、远行的人、思妇等多重意象，情感细腻，意境深远，表达了对时光流转、人生无常的感慨，以及对远方亲人的深切思念。全诗情感与自然景象和谐交融，是中国古典诗歌中不可多得的佳作。";

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                char[] chars = text.toCharArray();
                for (char aChar : chars) {
                    String s = new String(new char[]{aChar});
                    System.out.println(s);
                    sseEmitter.send(SseEmitter.event().data(s));
                    Thread.sleep(50);
                }
                // 数据发送完成后，关闭连接
                sseEmitter.complete();
            }
        }).start();

        return sseEmitter;
    }

}
