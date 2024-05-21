package com.playtube;

import com.playtube.controller.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableMBeanExport
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class PlayTubeApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(PlayTubeApp.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
