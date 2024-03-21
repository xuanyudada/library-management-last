package org.example.springboot.service;

import org.example.springboot.controller.request.UserPageRequest;
import org.example.springboot.entity.User;

import java.util.List;

public interface IUserService {
    List<User> list();


    Object page(UserPageRequest userPageRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);
}
