package com.baizhi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2017/7/2.
 */
@Document(collection = "t_server")
public class Server {
    @Id
    private String id;
    private Integer lev;

    public Server() {
    }

    public Server(String id, Integer lev) {
        this.id = id;
        this.lev = lev;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id='" + id + '\'' +
                ", lev=" + lev +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }
}
