package com.west2.security;

import com.west2.domain.User;
import com.west2.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 1
 * 自定义UserDetailsService对象
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("根据用户名查询用户：{}", username);
        // 1.根据用户名查询用户
        User user = userService.getUserByUsername(username);
        log.info("根据用户名查询用户：{}", user);
        // 2.判断用户是否存在
        if (user == null) {
            log.info("用户名不存在：{}", username);
            throw new UsernameNotFoundException("用户名不存在");
        }
        log.info("用户信息：{}", user);
        // 3.返回UserDetails对象
        return new MyUserDetails(user, userService.getRolesById(user.getId()));
    }
}
