package org.example.springboot.service;

import com.github.pagehelper.PageInfo;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Borrow;
import org.example.springboot.entity.Retur;

import java.util.List;

public interface IBorrowService {
    List<Borrow> list();

    PageInfo<Borrow> page(BaseRequest BaseRequest);

    void save(Borrow obj);

    PageInfo<Retur> pageRetur(BaseRequest BaseRequest);

    void saveRetur(Retur obj);

    Borrow getById(Integer id);

    void update(Borrow obj);

    void deleteById(Integer id);

    void deleterReturById(Integer id);
}
