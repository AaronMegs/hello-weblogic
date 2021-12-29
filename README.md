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
- 测试适用于weblogic版本 12c，其他weblogic版本未测试

