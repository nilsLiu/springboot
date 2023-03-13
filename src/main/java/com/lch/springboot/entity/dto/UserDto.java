package com.lch.springboot.entity.dto;

import lombok.Data;

/**
 * 接受前端登录请求的数据
 */
@Data
public class UserDto {
    private String username;
    private String password;
    private String id;
    private String token;
}
