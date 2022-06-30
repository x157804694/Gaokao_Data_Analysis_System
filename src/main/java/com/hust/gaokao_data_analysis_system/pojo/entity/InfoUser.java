package com.hust.gaokao_data_analysis_system.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.hust.gaokao_data_analysis_system.common.BasicClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName t_info_user
 */
@TableName(value ="t_info_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoUser extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "is_admin")
    private Integer is_admin;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}