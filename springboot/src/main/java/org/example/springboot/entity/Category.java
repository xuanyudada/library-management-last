package org.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.util.List;

@Data
public class Category {
    private Integer id;
    private String name;
    private String remark;
    private Integer pid;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  createtime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate uptatetime;

    private List<Category> children;
}
