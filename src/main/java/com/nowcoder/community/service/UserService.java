package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;

import java.util.Map;

public interface UserService {

    User findUserById(Integer userId);

    Map<String, Object> register(User user);

    int activation(Integer userId,String code);
}
