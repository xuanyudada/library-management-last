package org.example.springboot.service;

import com.github.pagehelper.PageInfo;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Book;

import java.util.List;

public interface IBookService {
    List<Book> list();

    PageInfo<Book> page(BaseRequest BaseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void update(Book obj);

    void deleteById(Integer id);
}
