package com.whosly.rapid.data.log;

import com.whosly.rapid.data.log.app.StartDemoApplicationTest;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/192:59 下午
 * @inc
 * @category
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartDemoApplicationTest.class)
@ActiveProfiles("dev")
public class SpringBaseTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
