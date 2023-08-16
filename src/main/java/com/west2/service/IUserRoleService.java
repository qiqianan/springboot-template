package com.west2.service;

import com.west2.domain.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-08-04
 */
public interface IUserRoleService extends IService<UserRole> {

    List<Long> getRoleIdsByUserId(Long id);
}
