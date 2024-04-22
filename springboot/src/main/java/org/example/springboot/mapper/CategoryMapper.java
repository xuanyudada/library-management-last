package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.controller.request.PasswordRequest;
import org.example.springboot.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> list();

    List<Category> listByCondition(BaseRequest baseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void updateById(Category obj);

    void deleteById(Integer id);
}
