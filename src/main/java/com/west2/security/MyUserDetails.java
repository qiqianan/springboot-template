package com.west2.security;

import com.west2.domain.Roles;
import com.west2.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author 1
 * 自定义UserDetails对象
 */
public class MyUserDetails implements UserDetails {

    private User user;
    private List<Roles> permissions;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public MyUserDetails(User user, List<Roles> permissions) {
        this.user = user;
        this.permissions = permissions;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public MyUserDetails(User user, List<Roles> permissions, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.user = user;
        this.permissions = permissions;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    /**
     * 返回一个表示用户拥有的权限的集合
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream().filter(permission -> permission.getValue() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue())).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * 账户是否被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     * 是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
