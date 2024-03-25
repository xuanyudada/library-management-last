package org.example.springboot.service;

import com.github.pagehelper.PageInfo;
import org.example.springboot.controller.dto.LoginDTO;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.controller.request.LoginRequest;
import org.example.springboot.entity.Admin;

import java.util.List;

public interface IAdminService {
    List<Admin> list();


    PageInfo<Admin> page(BaseRequest BaseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void update(Admin admin);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest request);
}
