package com.baizhi.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/2.
 */
public class Token {

    private String name;
    private String pwd;
    private String ip;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    public Token() {
    }

    public Token( String name, String pwd, String ip, Date createTime) {
        this.name = name;
        this.pwd = pwd;
        this.ip = ip;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
