package com.west2.controller;

import com.west2.utils.JwtUtil;
import com.west2.common.HttpResult;
import com.west2.domain.dto.UserLoginDto;
import com.west2.domain.dto.UserRegisterDto;
import com.west2.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 1
 */
@Slf4j
@RestController
@Api(value = "SecurityController", tags = "登录注册接口")
public class SecurityController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/ping")
    @PreAuthorize("hasAuthority('USER')")
    public String ping(){
        return "pong";
    }


    /**
     * 注册
     */
    @ApiOperation(value = "注册用户", notes = "注册用户")
    @PostMapping("/register")
    public HttpResult register(@RequestBody UserRegisterDto userRegisterDto){
        log.info("注册用户：{}", userRegisterDto);
        userService.register(userRegisterDto);
        return HttpResult.ok();
    }

    /**
     * 登录
     */
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/login")
    public HttpResult login(@RequestBody UserLoginDto userLoginDto){
        log.info("登录用户：{}", userLoginDto);
        return HttpResult.get(HttpResult.CODE_SUCCESS,"登录成功",userService.login(userLoginDto));
    }
}
