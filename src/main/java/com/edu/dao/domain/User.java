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
}
