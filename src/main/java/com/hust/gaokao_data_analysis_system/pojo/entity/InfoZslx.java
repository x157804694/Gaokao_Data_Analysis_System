package com.hust.gaokao_data_analysis_system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.hust.gaokao_data_analysis_system.common.BasicClass;
import lombok.Data;

/**
 * 
 * @TableName t_info_zslx
 */
@TableName(value ="t_info_zslx")
@Data
public class InfoZslx extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "zslx_code")
    private Integer zslx_code;

    /**
     * 
     */
    @TableField(value = "zslx_name")
    private String zslx_name;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}