package org.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import org.example.springboot.common.Result;
import org.example.springboot.controller.request.CategoryPageRequest;
import org.example.springboot.entity.Category;
import org.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;



    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Category obj = categoryService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category obj) {
        categoryService.update(obj);
        return Result.success();
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Category obj) {
        categoryService.save(obj);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }
    @GetMapping("/tree")
    public Result tree() {
        List<Category> list = categoryService.list();

        //对list操作
        List<Category> treeList = list.stream().filter(v -> v.getPid() == null).collect(Collectors.toList());   //第一层
        //比如    递归实现children列表的查询
        return Result.success(createTree(null,list));   //null表示从第一级开始递归
    }

    //完全递归的方法来实现递归树
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories){
            if (pid == null) {
                if (category.getPid() == null) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(),categories));
                }
            }else {
                if (pid.equals(category.getPid())){
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(),categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())){
                    category.setChildren(null);
            }
        }
        return treeList;
    }

    @GetMapping("/page")
    public Result page(CategoryPageRequest pageRequest) {
        return Result.success(categoryService.page(pageRequest));
    }
}