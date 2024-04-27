package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Borrow;

import java.util.List;

@Mapper
public interface BorrowMapper {

    List<Borrow> list();

    List<Borrow> listByCondition(BaseRequest baseRequest);

    void save(Borrow obj);

    Borrow getById(Integer id);

    void updateById(Borrow obj);

    void deleteById(Integer id);
}
