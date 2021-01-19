package com.whosly.rapid.data.log.annotation;

import com.whosly.rapid.data.log.registrar.EnableAutoLogAnnotationValueHolder;
import com.whosly.rapid.data.log.registrar.SpringMvcConfiguredRegistrar;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/1911:15 上午
 * @inc
 * @category
 */
public class AutoLogConfigurationSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        EnableAutoLogAnnotationValueHolder holder = getAnnotationVal(importingClassMetadata);

        List<String> result = new ArrayList<String>();
        result.add(SpringMvcConfiguredRegistrar.class.getName());

        return result.toArray(new String[result.size()]);
    }

    private EnableAutoLogAnnotationValueHolder getAnnotationVal(AnnotationMetadata metadata) {
        /* 获取注解配置 */
        AnnotationAttributes enableAutoLog = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(EnableAutoLog.class.getName()));

        String excludePathPatterns = enableAutoLog.getString("excludePathPatterns");
        String addPathPatterns = enableAutoLog.getString("addPathPatterns");

        EnableAutoLogAnnotationValueHolder holder = EnableAutoLogAnnotationValueHolder.builder()
                .excludePathPatterns(excludePathPatterns)
                .addPathPatterns(addPathPatterns)
                .build();

        return holder;
    }

}
