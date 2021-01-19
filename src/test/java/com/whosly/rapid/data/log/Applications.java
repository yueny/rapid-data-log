package com.whosly.rapid.data.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/193:20 下午
 * @inc
 * @category
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.whosly.rapid.data"},
        exclude = { DataSourceAutoConfiguration.class})
public class Applications {

    public static void main(String[] args) {
        try{
            ApplicationContext context = SpringApplication.run(Applications.class, args);
            String serverPort = context.getEnvironment().getProperty("server.port");
            log.info("mblog started at http://localhost:" + serverPort);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
