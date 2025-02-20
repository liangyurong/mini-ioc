package com.lyr;

import com.lyr.bean.MiniApplicationContext;
import com.lyr.example.UserService;

public class MiniIocApplication {
    public static void main(String[] args) {
        MiniApplicationContext context = new MiniApplicationContext("com.lyr");
        UserService userService = context.getBean("UserService", UserService.class);
        userService.print("John");
    }
}
