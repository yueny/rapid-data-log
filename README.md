## log增强扩展包
<dependency>
	<groupId>com.yueny.rapid.data</groupId>
	<artifactId>rapid-data-log-core</artifactId>
	<version>1.0.1-RELEASE</version>
</dependency>

## Version
* 1.0.1-RELEASE


##使用说明
# com.yueny.rapid.data.log.trace 日志链跟踪
# com.yueny.rapid.data.log.px 日志打印增强

#针对dobbo的扩展


#针对http的web扩展


# how-to-used

## stat 统计监控

## trace logId
> 1 依赖jar服务

`<dependency>
    <groupId>com.yueny.rapid.data</groupId>
    <artifactId>rapid-data-log-core</artifactId>
    <version>x.x.x</version>
</dependency>` 
> 2 修改日志配置文件。 如 logback.xml

每一个appender的pattern增加`[%X{ctxTraceId}][%X{ctxLogId}]`
如： 
```
<property name="DEFAULT_PATTERN" value="%d %-5level [%X{ctxTraceId}][%X{ctxLogId}][%thread] %logger{5} - %msg%n"/>

```

> 3  修改服务选项配置

- **Dubbo服务**：
增加 `<dubbo:provider filter="mdcLogFilter"/>`
或者`<dubbo:consumer filter="mdcLogFilter"/>`
- **web服务** ：
拦截器配置，并放在拦截器第一序列。
`<mvc:interceptors>
	<mvc:interceptor>
		<mvc:mapping path="/**"/>
		 <mvc:exclude-mapping path="/assets/**" /> 
		<bean class="com.yueny.rapid.data.log.trace.web.filter.mdc.WebLogMdcHandlerInterceptor" />
	</mvc:interceptor>
</mvc:interceptors>`	
- **线程内子线程** ：

# 同步历史
20190703 1.0.1-RELEASE 同步完成