# hello-weblogic
SpringBoot + weblogic + Oracle 实现示例
## 注意事项
- controller层需要放到servlet 配置文件同层下，否则在生产模式的12c.2.1.1.0 版本下调用不到接口

- SpringBoot版本小于2.2.x，否则可能在部署启动时报 无法解析module-info.class 文件的错误

- 启动类文件需要实现 `WebApplicationInitializer`（SpringBoot要求）: `public class HelloWeblogicApplication extends SpringBootServletInitializer implements WebApplicationInitializer`

- weblogic.xml配置文件中
  - `<wls:context-root>/weblogic-demo</wls:context-root>` 是提供服务时的入口目录，如调用接口： curl http://127.0.0.1:7001/weblogic-demo/ping
  - 使用WEB-INF下依赖：
    ```xml
    <wls:container-descriptor>
            <wls:prefer-application-packages>
                <wls:package-name>org.slf4j.*</wls:package-name>
                <wls:package-name>com.fasterxml</wls:package-name>
                <wls:package-name>org.springframework.*</wls:package-name>
            </wls:prefer-application-packages>
    </wls:container-descriptor>
  
- pom中需要排除 `spring-boot-started-web` 下的 `spring-boot-starter-tomcat` 依赖并新增加 `spring-boot-starter-tomcat` 依赖

- weblogic 生产模式与开发模式限制及相互转换

  - 限制：**生产模式下不能使用`自动部署`功能，开发模式下可以使用自动部署功能**，自动部署功能是将 `war包` 上传到 `$WEBLOGIC_HOME/domain/base_domain/autodeploy` 目录下即可

  - 生产模式 -->> 开发模式
  
    > 1、将domain路径下`%DOMAIN_HOME%\bin\setDomainEnv.cmd`文件 `set PRODUCTION_MODE=true` 更改为 `set PRODUCTION_MODE=false 或set PRODUCTION_MODE=`
    >
    > 2、将`%DOMAIN_HOME%\config\config.xml`文件中`<production-mode-enabled>true</production-mode-enabled>`更改为`<production-mode-enabled>false</production-mode-enabled>`或者`直接删掉`。

  - 开发模式 -->> 生产模式

    > 1、将domain路径下`%DOMAIN_HOME%\bin\setDomainEnv.cmd`文件`set PRODUCTION_MODE= 或 set PRODUCTION_MODE=false`更改为` set PRODUCTION_MODE=true`
  
- 测试适用于weblogic版本 12c，其他weblogic版本未测试

