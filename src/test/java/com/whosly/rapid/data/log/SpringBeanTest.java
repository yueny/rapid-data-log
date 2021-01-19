package com.whosly.rapid.data.log;

import com.whosly.rapid.data.log.registrar.AutoLogWebMvcConfigurationAdapter;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/193:00 下午
 * @inc
 * @category
 */
public class SpringBeanTest extends SpringBaseTest {

    @Resource
    private AutoLogWebMvcConfigurationAdapter autoLogWebMvcConfigurationAdapter;

    @Test
    public void test() {
        //.
        Assert.assertTrue(autoLogWebMvcConfigurationAdapter != null);
    }

}
