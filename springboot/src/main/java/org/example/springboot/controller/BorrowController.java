package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.controller.request.BorrowPageRequest;
import org.example.springboot.entity.Borrow;
import org.example.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    IBorrowService borrowService;



    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Borrow obj = borrowService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Borrow obj) {
        borrowService.update(obj);
        return Result.success();
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Integer id) {
        borrowService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Borrow obj) {
        borrowService.save(obj);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        List<Borrow> list = borrowService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(BorrowPageRequest pageRequest) {
        return Result.success(borrowService.page(pageRequest));
    }
}
