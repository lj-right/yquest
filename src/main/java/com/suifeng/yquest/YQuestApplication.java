package com.suifeng.yquest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.suifeng")
@MapperScan("com.suifeng.**.dao")
public class YQuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(YQuestApplication.class, args);
    }

}
