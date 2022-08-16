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
 * @TableName t_school_qj_major
 */
@TableName(value ="t_school_qj_major")
@Data
public class SchoolQjMajor extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "school_qj_major_code", type = IdType.AUTO)
    private Integer school_qj_major_code;

    /**
     *
     */
    @TableField(value = "school_qj_major_school")
    private Integer school_qj_major_school;

    /**
     * 
     */
    @TableField(value = "school_qj_major_major")
    private String school_qj_major_major;

    /**
     * 
     */
    @TableField(value = "school_qj_major_year")
    private String school_qj_major_year;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}