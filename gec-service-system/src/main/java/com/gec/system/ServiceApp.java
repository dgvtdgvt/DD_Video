package com.gec.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:gec
 * @Date:2023/6/16
 * @Description:code
 * @version:1.0
 * springboot项目的启动器类
 */
@SpringBootApplication
@MapperScan("com.gec.system.mapper")
public class ServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApp.class,args);
    }
}
