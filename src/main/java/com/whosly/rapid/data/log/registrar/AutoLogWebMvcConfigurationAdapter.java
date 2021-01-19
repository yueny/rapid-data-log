package com.whosly.rapid.data.log.registrar;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.whosly.rapid.data.log.trace.filter.mdc.web.WebLogMdcHandlerInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/1911:37 上午
 * @inc
 * @category
 */
public class AutoLogWebMvcConfigurationAdapter extends WebMvcConfigurerAdapter {
    // 可能为 null
    private final EnableAutoLogAnnotationValueHolder valueHolder;

    AutoLogWebMvcConfigurationAdapter(EnableAutoLogAnnotationValueHolder valueHolder) {
        this.valueHolder = valueHolder;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String excludePathPatternVal = valueHolder.getExcludePathPatterns();

        WebLogMdcHandlerInterceptor interceptor = new WebLogMdcHandlerInterceptor();

        // excludePathPatterns  为空的处理， 不设置 excludePathPatterns
        if(StringUtils.isEmpty(excludePathPatternVal)){
            registry.addInterceptor(interceptor)
                    .addPathPatterns(valueHolder.getAddPathPatterns());
            return;
        }

        List<String> excludePathPatterns = Splitter.on(";")
                // 判断是否有空格字符，如果有空格字符，去除空格字符
                .trimResults().omitEmptyStrings()
                .splitToList(excludePathPatternVal);
        registry.addInterceptor(interceptor)
                .excludePathPatterns(toArray(excludePathPatterns))
                .addPathPatterns(valueHolder.getAddPathPatterns());
    }

    private String[] toArray(List<String> list) {
        return (CollectionUtils.isEmpty(list) ? null : list.toArray(new String[list.size()]));
    }
}
