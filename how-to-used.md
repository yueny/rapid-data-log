# log增强扩展包

## stat 统计监控

## trace logId
> 1 依赖jar服务

`<dependency>
    <groupId>com.yueny.rapid.data</groupId>
    <artifactId>rapid-data-log-core</artifactId>
    <version>x.x.x</version>
</dependency>` 
> 2 修改日志配置文件。 如 logback.xml

每一个appender的pattern增加`[%X{ctxTraceId}][%X{ctxEventId}]`
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
