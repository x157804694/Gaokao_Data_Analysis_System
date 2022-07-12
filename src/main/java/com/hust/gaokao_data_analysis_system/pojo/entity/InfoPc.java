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
 * @TableName t_info_pc
 */
@TableName(value ="t_info_pc")
@Data
public class InfoPc extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "pc_code", type = IdType.AUTO)
    private Integer pc_code;

    /**
     * 
     */
    @TableField(value = "pc_name")
    private String pc_name;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}