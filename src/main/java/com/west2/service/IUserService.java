package com.west2.service;

import com.west2.domain.Roles;
import com.west2.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.west2.domain.dto.UserLoginDto;
import com.west2.domain.dto.UserRegisterDto;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-08-04
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

/**
     * 根据用户id查询用户的所有角色
     * @param id
     * @return
     */
    List<Roles> getRolesById(Long id);


    /**
     * 注册
     * @param userRegisterDto
     */
    void register(UserRegisterDto userRegisterDto);

    /**
     * 登录
     * @param userLoginDto
     * @return
     */
    String login(UserLoginDto userLoginDto);
}
