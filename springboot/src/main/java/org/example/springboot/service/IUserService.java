package org.example.springboot.service;

import com.github.pagehelper.PageInfo;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.User;

import java.util.List;

public interface IUserService {
    List<User> list();


    PageInfo<User> page(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    void handleAccount(User user);
}
