package org.example.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Category;
import org.example.springboot.mapper.CategoryMapper;
import org.example.springboot.service.ICategoryService;
import org.springframework.stereotype.Service;
import sun.util.resources.LocaleData;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CategoryService implements ICategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        //自关联查询
        List<Category> categories = categoryMapper.listByCondition(baseRequest);
        return new PageInfo<>(categories);
    }

    @Override
    public void save(Category obj) {
        categoryMapper.save(obj);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category obj) {
        obj.setUptatetime(LocalDate.now());
        categoryMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
