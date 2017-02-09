package org.panda.pdaily;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 程序的入口
 * Created by fangzanpan on 2017/2/4.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan("org.panda.pdaily")
// 扫描mapper的地址
@MapperScan("org.panda.pdaily.mapper")
public class PDailyApplication {

    private static Logger sLog = Logger.getLogger(PDailyApplication.class);

    public static void main(String[] args) {
        System.out.println("Application run.");
        SpringApplication.run(PDailyApplication.class, args);

        sLog.info("Application run.");
    }

    @RequestMapping("/")
    public String home() {
        sLog.info("Hello world!");
        return "Hello world!";
    }
}
