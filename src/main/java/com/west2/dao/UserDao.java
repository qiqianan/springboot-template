package com.west2.dao;

import com.west2.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 翁鹏
 * @since 2023-08-04
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
