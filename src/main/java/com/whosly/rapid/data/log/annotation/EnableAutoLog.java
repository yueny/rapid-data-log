package com.whosly.rapid.data.log.annotation;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动启用
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/1911:14 上午
 * @inc
 * @category
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoLogConfigurationSelector.class)
public @interface EnableAutoLog {

    /**
     * AutoLog WebMvc 的 excludePathPatterns 数组, 分号;  分隔
     *
     * 如
     * <pre>
     *     "favicon.ico;/assets/**;/open/**;/getKaptcha";/websocket"
     * </pre>
     */
    String excludePathPatterns() default "";

    /**
     * AutoLog WebMvc 的 addPathPatterns 数组, 分号;  分隔
     *
     * 如
     * <pre>
     *     "favicon.ico;/assets/**;/open/**;/getKaptcha";/websocket"
     *     "/**"
     * </pre>
     */
    String addPathPatterns() default "/**";

}
