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
 * @TableName t_school_dual_subject
 */
@TableName(value ="t_school_dual_subject")
@Data
public class SchoolDualSubject extends BasicClass implements Serializable{
    /**
     * 
     */
    @TableId(value = "school_dual_subject_code", type = IdType.AUTO)
    private Integer school_dual_subject_code;


    /**
     * 
     */
    @TableField(value = "school_dual_subject_school_subject")
    private Integer school_dual_subject_school_subject;

    /**
     * 
     */
    @TableField(value = "school_dual_subject_year")
    private String school_dual_subject_year;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}