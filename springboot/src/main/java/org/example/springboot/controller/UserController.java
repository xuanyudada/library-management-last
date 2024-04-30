package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.controller.request.UserPageRequest;
import org.example.springboot.entity.User;
import org.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        User user =userService.getById(id);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        userService.save(user);
        return Result.success();
    }
    @PostMapping("/account")
    public Result account(@RequestBody User user){
        userService.handleAccount(user);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<User> users =userService.list();
        return Result.success(users);
    }
    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest){
        return Result.success(userService.page(userPageRequest));
    }
}
