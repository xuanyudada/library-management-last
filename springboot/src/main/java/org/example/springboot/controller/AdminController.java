package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.controller.dto.LoginDTO;
import org.example.springboot.controller.request.AdminPageRequest;
import org.example.springboot.controller.request.LoginRequest;
import org.example.springboot.entity.Admin;
import org.example.springboot.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request) {
        LoginDTO login = adminService.login(request);
        return Result.success(login);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Admin obj = adminService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Admin obj) {
        adminService.update(obj);
        return Result.success();
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Admin obj) {
        adminService.save(obj);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        List<Admin> list = adminService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(AdminPageRequest pageRequest) {
        return Result.success(adminService.page(pageRequest));
    }
}
