package com.example.shop.model;

public class User {
    
    private Long id;
    private String name;
    private String email;

    public User(){

    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    
}
