package com.whosly.rapid.data.log.registrar;

import com.whosly.rapid.data.log.annotation.EnableAutoLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StopWatch;

import java.util.UUID;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/1911:25 上午
 * @inc
 * @category
 */
@Slf4j
public class SpringMvcConfiguredRegistrar implements ImportBeanDefinitionRegistrar {
    protected String getTaskName(){
        return "SpringMvcConfiguredRegistrar";
    }

    /**
     * AnnotationMetadata参数用来获得当前配置类上的注解； BeanDefinittionRegistry参数用来注册Bean
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        StopWatch stopWatch = new StopWatch(UUID.randomUUID().toString());
        stopWatch.start(getTaskName());

        EnableAutoLogAnnotationValueHolder holder = getAnnotationVal(importingClassMetadata);
        if (registry.containsBeanDefinition(getTaskName())) {
            BeanDefinition definition = registry.getBeanDefinition(getTaskName());
            ConstructorArgumentValues.ValueHolder constructorArguments = definition.getConstructorArgumentValues()
                    .getGenericArgumentValue(EnableAutoLogAnnotationValueHolder.class);

            constructorArguments.setValue(holder);
        } else {
            registerBeanDefinitions(registry, holder);
        }

        if(stopWatch != null){
            stopWatch.stop();
            log.debug("{} 注册完成. 初始化耗时占用: {} ms, {}",
                    getTaskName(), stopWatch.getTotalTimeMillis(), stopWatch.prettyPrint());
        }else{
            log.debug("{} 注册完成.", getTaskName());
        }
    }

    private void registerBeanDefinitions(BeanDefinitionRegistry registry, EnableAutoLogAnnotationValueHolder holder) {
        /* 注册 xxx， 起到 @Bean 的作用 */
        /*
        RootBeanDefinition用来在配置阶段进行注册bean definition。
        从spring 2.5后，编写注册bean definition有了更好的的方法：GenericBeanDefinition。
        GenericBeanDefinition支持动态定义父类依赖，而非硬编码作为root bean definition。
        */
        // BeanDefinition beanDefinition = new RootBeanDefinition(ManageSpringBeans.class);
        // or
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(AutoLogWebMvcConfigurationAdapter.class);
        beanDefinition.setBeanClassName(AutoLogWebMvcConfigurationAdapter.class.getName());
        beanDefinition.getConstructorArgumentValues()
                .addGenericArgumentValue(holder);

        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(beanDefinition.getBeanClassName(), beanDefinition);
    }

    private EnableAutoLogAnnotationValueHolder getAnnotationVal(AnnotationMetadata metadata) {
        /* 获取注解配置 */
        AnnotationAttributes enableAutoLog = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(EnableAutoLog.class.getName()));
        if(enableAutoLog == null) {
            return null;
        }

        String excludePathPatterns = enableAutoLog.getString("excludePathPatterns");
        String addPathPatterns = enableAutoLog.getString("addPathPatterns");

        EnableAutoLogAnnotationValueHolder holder = EnableAutoLogAnnotationValueHolder.builder()
                .excludePathPatterns(excludePathPatterns)
                .addPathPatterns(addPathPatterns)
                .build();

        return holder;
    }

}
