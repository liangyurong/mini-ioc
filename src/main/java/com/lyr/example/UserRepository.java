package com.lyr.example;

import com.lyr.annotation.MiniComponent;

@MiniComponent("UserRepository")
public class UserRepository {
    public void print(String username) {
        System.out.println("示例UserRepository的save(): " + username);
    }

}
