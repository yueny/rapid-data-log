# log增强扩展

## Table of Contents
### 使用方式
<details>
<summary>详细信息</summary>

- [`依赖jar服务`](#依赖jar服务)
- [`修改日志配置文件`](#修改日志配置文件)
- [`修改服务选项配置`](#修改服务选项配置)

</details>

### 包路径说明
<details>
<summary>详细信息</summary>

- [`日志链跟踪`](#日志链跟踪)
- [`针对dobbo的扩展`](#针对dobbo的扩展)
- [`针对http的web扩展`](#针对http的web扩展)
- [`how-to-used`](#how-to-used)
- [`stat统计监控`](#stat统计监控)
- [`trace-logId`](#trace-logId)

</details>

### 版本信息
<details>
<summary>详细信息</summary>

- [`版本信息`](#版本信息)

</details>
 
### 同步历史
<details>
<summary>详细信息</summary>

- [`同步历史`](#同步历史)

</details>


## 使用方式
### 依赖jar服务
引入对应版本的pom 依赖

### 修改日志配置文件 
如 logback.xml

每一个appender的pattern增加`[%X{ctxTraceId}][%X{ctxLogId}]`
如： 
```
<property name="DEFAULT_PATTERN" value="%d %-5level [%X{ctxTraceId}][%X{ctxLogId}][%thread] %logger{5} - %msg%n"/>

```

### 修改服务选项配置

- **Dubbo服务**：
增加 `<dubbo:provider filter="mdcLogFilter"/>`
或者`<dubbo:consumer filter="mdcLogFilter"/>`

- **web服务** ：
拦截器配置，并放在拦截器第一序列。
`<mvc:interceptors>
	<mvc:interceptor>
		<mvc:mapping path="/**"/>
		 <mvc:exclude-mapping path="/assets/**" /> 
		<bean class="com.whosly.rapid.data.log.trace.mdc.WebLogMdcHandlerInterceptor" />
	</mvc:interceptor>
</mvc:interceptors>`	
- **线程内子线程** ：


## 包路径说明
### 日志链跟踪
com.yueny.rapid.data.log.trace 

### 日志打印增强
com.yueny.rapid.data.log.px 

### 针对dobbo的扩展


### 针对http的web扩展


### how-to-used

### stat 统计监控

### trace logId

<br>[⬆ 回到顶部](#table-of-contents)

## 版本信息
[版本信息](version-history.md)


## 同步历史
[同步历史](syn-history.md)
