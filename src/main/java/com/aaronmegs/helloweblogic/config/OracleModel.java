package com.aaronmegs.helloweblogic.config;

/**
 * @author AaronMegs
 * @create 2021/10/26
 */
//@Data - 不使用lombok是因为 weblogic12c.2.1.1.0 生产模式下不能识别 module-info.class 类
public class OracleModel {
    private String url;

    private String username;

    private String password;

    private String port;

    private String sid;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
