package com.suifeng.yquest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.suifeng")
@MapperScan("com.suifeng.**.dao")
@EnableTransactionManagement //开启注解方式的事务管理
public class YQuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(YQuestApplication.class, args);
    }

}
