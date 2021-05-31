package com.example.cookup.Logic;


import lombok.Data;

@Data
public class User {
    private String name;
    private String Uuid;
    private String token;

    public User(String name, String Uuid, String token){
        this.name = name;
        this.Uuid = Uuid;
        this.token = token;
    }
}
