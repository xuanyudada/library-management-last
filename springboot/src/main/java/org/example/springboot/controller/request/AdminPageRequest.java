package org.example.springboot.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminPageRequest extends BaseRequest {
    private String username;
//    private String name;
    private String phone;
    private String email;
}
