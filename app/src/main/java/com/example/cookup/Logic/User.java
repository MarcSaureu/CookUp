package com.example.cookup.Logic;


import lombok.Data;

@Data
public class User {
    private String name;
    private String Uuid;

    public User(String name, String Uuid){
        this.name = name;
        this.Uuid = Uuid;
    }
}
