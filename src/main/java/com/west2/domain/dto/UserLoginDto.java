package com.west2.domain.dto;

import com.west2.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 1
 */
@Data
public class UserLoginDto extends User {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;




}
