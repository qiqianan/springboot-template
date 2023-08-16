package com.west2.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.west2.utils.JwtUtil;
import com.west2.dao.RolesDao;
import com.west2.domain.Roles;
import com.west2.domain.User;
import com.west2.dao.UserDao;
import com.west2.domain.dto.UserLoginDto;
import com.west2.domain.dto.UserRegisterDto;
import com.west2.security.MyUserDetails;
import com.west2.security.UserDetailsServiceImpl;
import com.west2.service.IUserRoleService;
import com.west2.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-08-04
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private RolesDao rolesDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;



    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        log.info("根据用户名查询用户 : " + username);
        return this.getOne(wrapper);
    }

    @Override
    public List<Roles> getRolesById(Long id) {
        // 1.根据用户id在用户-角色表查询所有的角色id
        List<Long> roleIds = userRoleService.getRoleIdsByUserId(id);
        log.info("根据用户id查询角色id列表 : " + roleIds);
        // 2.根据角色id在角色表查询所有的权限id
        // 3.返回权限列表
        return rolesDao.selectBatchIds(roleIds);
    }

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        // 密码加密
        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        this.save(userRegisterDto);
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        String token = null;
        MyUserDetails myUserDetails = userDetailsService.loadUserByUsername(userLoginDto.getUsername());
        log.info("登录用户信息:{}", myUserDetails.toString());
//        if (!passwordEncoder.matches(userLoginDto.getPassword(), myUserDetails.getPassword())) {
//            throw new BadCredentialsException("密码不正确");
//        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 使用fastjson序列化userDetails
        String userInfo = JSON.toJSONString(myUserDetails);
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) myUserDetails.getAuthorities();
        List<String> authList = authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
        // 生成token
        token = jwtUtil.createJwt(userInfo,authList);
        // 将token存入redis
        stringRedisTemplate.opsForValue().set(jwtUtil.TOKEN_CACHE_PREFIX + token, userInfo,2, TimeUnit.HOURS);
        return token;
    }
}
