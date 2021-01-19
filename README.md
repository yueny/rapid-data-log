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

- [`版本信息`](#version)

</details>
 
### 同步历史
<details>
<summary>详细信息</summary>

- [`同步历史`](#history)

</details>


## 使用方式
### 依赖jar服务
引入对应版本的pom 依赖

### 修改日志配置文件 
如 logback.xml

每一个appender的pattern增加 `[%X{ctxTraceId}][%X{ctxLogId}]`
如： 
```
<property name="DEFAULT_PATTERN" value="%d %-5level [%X{ctxTraceId}][%X{ctxLogId}][%thread] %logger{5} - %msg%n"/>

```

### 在启动类上增加注解配置 @EnableAutoLog，以实现自动注入
示例代码
```aidl

@SpringBootApplication(scanBasePackages = {"xxxxxx"})
@EnableAutoLog(excludePathPatterns="favicon.ico;/assets/**;")
public class BlogAdminApplication {

    public static void main(String[] args) {
        try{
            ApplicationContext context = SpringApplication.run(BlogAdminApplication.class, args);
            String serverPort = context.getEnvironment().getProperty("server.port");
            log.info("mblog started at http://localhost:" + serverPort);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

```

### 修改服务选项配置
- **Dubbo服务**：
不需要任何配置，会自动在provider和consumer追加上下文

如果使用了 dubbo 的项目，不需要使用此功能，请做排除配置。

示例如下：
```aidl
<dubbo:provider filter="-mdcLogFilter"/>
或者
<dubbo:consumer filter="-mdcLogFilter"/>
```
- **web服务** ：
不需要任何配置，会自动追加上下文

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

## <a name="version"></a>  版本信息
[版本信息](version-history.md)


## <a name="history"></a>  同步历史
[同步历史](syn-history.md)
