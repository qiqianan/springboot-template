package com.west2.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 翁鹏
 * @since 2023-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色的英文名
     */
    private String value;

    /**
     * 角色数量
     */
    private Integer userCount;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
