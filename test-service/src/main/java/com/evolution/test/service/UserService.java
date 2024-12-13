package com.evolution.test.service;

import com.evolution.test.mapper.UserMapper;
import com.evolution.test.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> selectUser() {
        return userMapper.selectAll();
    }

    public User getById(Long id){
        return userMapper.getById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

}
