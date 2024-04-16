package org.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.springboot.common.Result;
import org.example.springboot.controller.dto.LoginDTO;
import org.example.springboot.controller.request.BaseRequest;
import org.example.springboot.controller.request.LoginRequest;
import org.example.springboot.controller.request.PasswordRequest;
import org.example.springboot.entity.Admin;
import org.example.springboot.exeption.ServiceException;
import org.example.springboot.mapper.AdminMapper;
import org.example.springboot.service.IAdminService;
import org.example.springboot.untils.TokenUtils;
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

    private static final String DEFAULT_PASS = "123";
    private static final String PASS_SALT = "YU";

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
        if (StrUtil.isBlankIfStr(obj.getPassword())) {
            obj.setPassword(DEFAULT_PASS);
        }
        obj.setPassword(securePass(obj.getPassword()));//设置加密，加盐
        Date date = new Date();
        //当做卡号处理

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
        request.setPassword(securePass(request.getPassword()));
        Admin admin = adminMapper.getByUsernameAndPassword(request.getUsername(),request.getPassword());
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!admin.isStatus()) {
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);
        //生成Token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());
        loginDTO.setToken(token);
        return loginDTO;
    }

    @Override
    public void changePass(PasswordRequest request) {
        //注意 对新的密码加密
        request.setNewPass(securePass(request.getNewPass()));
        int count = adminMapper.updatePassword(request);
        if (count <=0){
            throw new ServiceException("修改密码失败");
        }
    }

    private String securePass(String password) {
        return SecureUtil.md5(password + PASS_SALT);
    }
}
