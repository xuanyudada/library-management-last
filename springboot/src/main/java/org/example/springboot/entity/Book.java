package org.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class Book  extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private String publishDate;
    private String author;
    private String publisher;
    private String category;
    private String bookNo;
    private String cover;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createtime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate updatetime;
    private List<String> categories;
    private Integer score;
    private Integer nums;
}
