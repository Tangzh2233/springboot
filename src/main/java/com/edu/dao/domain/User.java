package com.edu.dao.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/4.
 */
@Data
public class User implements Serializable{

    private static final long serialVersionUID = -3677823825852900686L;

    public User(){}
    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    private Integer id;
    private String username;
    private String password;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
