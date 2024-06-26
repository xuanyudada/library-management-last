package org.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.beans.binding.StringBinding;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Book;
import org.example.springboot.entity.Category;
import org.example.springboot.exeption.ServiceException;
import org.example.springboot.mapper.BookMapper;
import org.example.springboot.service.IBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {
    @Resource
    BookMapper bookMapper;

    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(bookMapper.listByCondition(baseRequest));
    }

    @Override
    public void save(Book obj) {
        try {
            obj.setCategory(category(obj.getCategories()));
            bookMapper.save(obj);
        }catch (Exception e){
            throw new ServiceException("数据插入错误",e);
        }

    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void update(Book obj) {
        try {
            obj.setCategory(category(obj.getCategories()));
            obj.setUpdatetime(LocalDate.now());
            bookMapper.updateById(obj);
        }catch (Exception e){
            throw new ServiceException("数据更新错误",e);
        }

    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }
    private String category(List<String> categories){
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)){
            categories.forEach(v -> sb.append(v).append(" > "));
            return sb.substring(0,sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }
}
