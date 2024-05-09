package org.example.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.entity.Book;
import org.example.springboot.entity.Borrow;
import org.example.springboot.entity.Retur;
import org.example.springboot.entity.User;
import org.example.springboot.exeption.ServiceException;
import org.example.springboot.mapper.BookMapper;
import org.example.springboot.mapper.BorrowMapper;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BorrowService implements IBorrowService {
    @Resource
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;


    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);
        for (Borrow borrow : borrows) {
            LocalDate returnDate = borrow.getReturnDate();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(returnDate)) {  //当前日期比归还日期小一天
                borrow.setNote("即将到期");
            } else if (now.isEqual(returnDate)) {
                borrow.setNote("已到期");
            } else if (now.isAfter(returnDate)) {
                borrow.setNote("已过期");
            } else {
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }

    @Override
    @Transactional
    public void save(Borrow obj) {
        //1.校验用户积分是否足够
        String userNo = obj.getUserNo();
        User user = userMapper.getByUsername(userNo);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
        //2.校验图书的数量是否足够
        Book book = bookMapper.getByNo(obj.getBookNo());
        if (Objects.isNull(book)) {
            throw new ServiceException("所借图书不存在");
        }
        //3.校验图书数量
        if (book.getNums() < 1) {
            throw new ServiceException("图书数量不足");
        }
        Integer account = user.getAccount();
        Integer score = book.getScore() * obj.getDays();// score=借1本的积分 * 天数
        //4.校验用户账户余额
        if (score > account) {
            throw new ServiceException("用户积分不足");
        }

        //5.更新余额
        user.setAccount(user.getAccount() - score);
        userMapper.updateById(user);
        //6.更新图书的数量
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);

        obj.setReturnDate(LocalDate.now().plus(obj.getDays(), ChronoUnit.DAYS));    //当前的日期 加days   得到归还的日期
        //7.新增借书记录
        borrowMapper.save(obj);
    }

    @Override
    public PageInfo<Retur> pageRetur(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(borrowMapper.listReturByCondition(baseRequest));
    }

    //还书逻辑
    @Transactional
    @Override
    public void saveRetur(Retur obj) {
        //改状态
        obj.setStatus("已归还");
        borrowMapper.updateStatus("已归还", obj.getId());       //前端传来的借书Id

//        obj.setId(null);
        obj.setRealDate(LocalDate.now());
        borrowMapper.saveRetur(obj);

        //增加图书数量
        bookMapper.updateNumByNo(obj.getBookNo());

        //返还和扣除用户积分
        Book book = bookMapper.getByNo(obj.getBookNo());
        if (book != null){
            long until = 0;
            if (obj.getRealDate().isBefore(obj.getReturnDate())) {
                until = obj.getRealDate().until(obj.getReturnDate(), ChronoUnit.DAYS); // 计算实际归还日期到应归还日期之间的天数
            } else if (obj.getRealDate().isAfter(obj.getReturnDate())) {    //逾期归还
                until = -obj.getReturnDate().until(obj.getRealDate(), ChronoUnit.DAYS); // 计算应归还日期到实际归还日期之间的天数，并且取负值
            }
            int score = (int) until * book.getScore();  //获取待归还的积分
            User user = userMapper.getByUsername(obj.getUserNo());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if (account <0){
                //锁定账号
                user.setStatus(false);
            }
            userMapper.updateById(user);
        }
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow obj) {
        obj.setUpdatetime(LocalDate.now());
        borrowMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }

    @Override
    public void deleterReturById(Integer id) {
        borrowMapper.deleteReturById(id);
    }

}
