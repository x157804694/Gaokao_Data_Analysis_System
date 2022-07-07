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
 * @TableName t_info_major
 */
@TableName(value ="t_info_major")
@Data
public class InfoMajor extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "major_code",type = IdType.AUTO)
    private Integer major_code;

    @TableField(value = "major_id")
    private String major_id;
    /**
     * 
     */
    @TableField(value = "major_name")
    private String major_name;


    /**
     * 
     */
    @TableField(value = "major_subject")
    private String major_subject;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}