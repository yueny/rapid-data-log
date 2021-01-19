package com.whosly.rapid.data.log.registrar;

import lombok.*;

/**
 * 注解的配置参数持有者
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/1911:40 上午
 * @inc
 * @category
 */
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnableAutoLogAnnotationValueHolder {
    /**
     * AutoLog WebMvc 的 excludePathPatterns 数组, 分号;  分隔
     *
     * 如
     * <pre>
     *     "favicon.ico;/assets/**;/open/**;/getKaptcha";/websocket"
     * </pre>
     */
    @NonNull
    @Getter
    private String excludePathPatterns;

    /**
     * AutoLog WebMvc 的 addPathPatterns 数组, 分号;  分隔
     *
     * 如
     * <pre>
     *     "favicon.ico;/assets/**;/open/**;/getKaptcha";/websocket"
     *     "/**"
     * </pre>
     */
    @NonNull
    @Getter
    private String addPathPatterns;

}

