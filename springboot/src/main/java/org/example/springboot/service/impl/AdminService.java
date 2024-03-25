package org.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.common.Result;
import org.example.springboot.controller.dto.LoginDTO;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.controller.request.LoginRequest;
import org.example.springboot.entity.Admin;
import org.example.springboot.exeption.ServiceException;
import org.example.springboot.mapper.AdminMapper;
import org.example.springboot.service.IAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admin = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admin);
    }

    @Override
    public void save(Admin obj) {
        Date date = new Date();
        //当做卡号处理
        obj.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        adminMapper.save(obj);
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin obj) {
        obj.setUpdatetime(new Date());
        adminMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request) {
        Admin admin = adminMapper.getByUsernameAndPassword(request);
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        LoginDTO loginDTO  = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);
        return loginDTO;
    }
}
