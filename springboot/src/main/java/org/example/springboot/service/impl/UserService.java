package org.example.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.springboot.controller.request.UserPageRequest;
import org.example.springboot.entity.User;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public Object page(UserPageRequest userPageRequest) {
        PageHelper.startPage(userPageRequest.getPageNum(),userPageRequest.getPageSize());
        List<User> users = userMapper.listByCondition(userPageRequest);
       return new PageInfo<>(users);
    }
}
