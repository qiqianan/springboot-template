package com.west2.config;

import com.west2.utils.JwtUtil;

import com.west2.security.JwtAuthFilter;
import com.west2.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {




    /**
     * 用户信息获取
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Redis操作
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * JWT工具类
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 密码加密器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * 配置SecurityFilterChain
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 配置过滤器链
        // 禁止使用csrf
        http.csrf().disable()
                // 禁止使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers(
                        // 放行登录接口
                        request -> "/login".equals(request.getRequestURI())
                                // 放行注册接口
                                || "/register".equals(request.getRequestURI())
                ).permitAll() // 放行以上接口
                // 放行静态资源
                .antMatchers("/static/**").permitAll()
                // 放行swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 添加自定义过滤器
                .addFilterBefore(new JwtAuthFilter(userDetailsServiceImpl, stringRedisTemplate, jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsServiceImpl);
        return http.build();

    }


    /**
     * 配置跨源访问(CORS)
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
