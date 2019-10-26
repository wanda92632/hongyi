package com.wanda.hongyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wanda.hongyi.mapper")
public class HongyiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HongyiApplication.class, args);
    }

}
