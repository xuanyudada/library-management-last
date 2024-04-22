package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.controller.request.LoginRequest;
import org.example.springboot.controller.request.PasswordRequest;
import org.example.springboot.entity.Admin;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<Admin> list();

    List<Admin> listByCondition(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void updateById(Admin obj);

    void deleteById(Integer id);

    Admin getByUsernameAndPassword(@Param("username")String username,@Param("password")String password);

    int updatePassword(PasswordRequest request);

    Admin getByUsername(String username);
}
