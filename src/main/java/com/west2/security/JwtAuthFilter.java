package com.west2.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.west2.common.HttpResult;
import com.west2.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 1
 * 自定义过滤器，用于校验token
 */
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private StringRedisTemplate stringRedisTemplate;

    private JwtUtil jwtUtil;

    public JwtAuthFilter(UserDetailsService userDetailsService, StringRedisTemplate stringRedisTemplate,JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 重写过滤器的doFilterInternal方法，实现自定义过滤器的逻辑
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token
        String token = request.getHeader( jwtUtil.TOKEN_HEADER);
        // 如果是登录、注册请求，直接放行
        String requestURI = request.getRequestURI();
        if ("/login".equals(requestURI) || "/register".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("token:{}", token);
        if (StringUtils.isBlank(token)) {
            HttpResult.sendResponse(response, HttpResult.error("token为空"));
            return;
        }
        //校验jwt
        boolean verifyResult = jwtUtil.verifyToken(token);
        if(!verifyResult){
            HttpResult.sendResponse(response, HttpResult.error("token非法"));
            return;
        }
        //判断redis是否存在token
        String redisToken = stringRedisTemplate.opsForValue().get(jwtUtil.TOKEN_CACHE_PREFIX + token);
        if(StringUtils.isEmpty(redisToken)){
            HttpResult.sendResponse(response, HttpResult.error("请重新登录"));
            return;
        }
        // 从token中获取用户信息
        String userInfo = jwtUtil.getUserInfoFromToken(token);
        List<String> userAuthList = jwtUtil.getUserAuthFromToken(token);
        log.info("userInfo:{}", userInfo);
        // 反序列化为MyUserDetails对象
        JSONObject myUserDetails = JSON.parseObject(userInfo);
        log.info("userDetails:{}", myUserDetails.toString());
        List<SimpleGrantedAuthority> authorityList=userAuthList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // 用户名密码认证成功后，将用户信息存入SecurityContext中，方便获取当前用户信息
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, myUserDetails,authorityList);
            // 保存了关于Web请求的详细信息，例如远程地址、会话ID、请求的URL等
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 将用户信息存入SecurityContext中，方便获取当前用户信息
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 放行
        filterChain.doFilter(request, response);
    }
}

