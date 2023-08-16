package com.west2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.west2.domain.UserRole;
import com.west2.dao.UserRoleDao;
import com.west2.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-08-04
 */
@Service
@Slf4j
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements IUserRoleService {

    @Override
    public List<Long> getRoleIdsByUserId(Long id) {
        // 1.根据用户id在用户-角色表查询所有的角色id
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoles = this.list(queryWrapper);
        log.info("用户角色信息：{}", userRoles);
        // 2.将所有的角色id放入一个集合中
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}
