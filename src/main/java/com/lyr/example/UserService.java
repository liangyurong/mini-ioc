package com.lyr.example;

import com.lyr.annotation.MiniAutowired;
import com.lyr.annotation.MiniComponent;

@MiniComponent // 默认驼峰命名
public class UserService {
    @MiniAutowired
    private UserRepository userRepository;

    public void print(String username) {
        userRepository.print(username);
    }
}
