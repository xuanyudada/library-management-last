package org.example.springboot.service;

import com.github.pagehelper.PageInfo;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> list();

    PageInfo<Category> page(BaseRequest BaseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void update(Category obj);

    void deleteById(Integer id);
}
