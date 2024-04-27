package org.example.springboot.service;

import com.github.pagehelper.PageInfo;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Borrow;

import java.util.List;

public interface IBorrowService {
    List<Borrow> list();

    PageInfo<Borrow> page(BaseRequest BaseRequest);

    void save(Borrow obj);

    Borrow getById(Integer id);

    void update(Borrow obj);

    void deleteById(Integer id);
}
