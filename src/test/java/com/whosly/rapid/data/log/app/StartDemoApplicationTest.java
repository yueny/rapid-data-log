package com.whosly.rapid.data.log.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/192:45 下午
 * @inc
 * @category
 */
@SpringBootApplication(scanBasePackages= {"com.whosly.rapid.data"},
        exclude = {DataSourceAutoConfiguration.class,
                RedisAutoConfiguration.class,
                RedisRepositoriesAutoConfiguration.class})
@Slf4j
public class StartDemoApplicationTest {
    /**
     *
     * @param args
     */
    public static void main( String[] args ) {
        ApplicationContext context = SpringApplication.run(StartDemoApplicationTest.class, args);

        String serverPort = context.getEnvironment().getProperty("server.port");

        log.info("Application started at http://localhost:" + serverPort);
    }
}