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
 * @TableName t_school_subject
 */
@TableName(value ="t_school_subject")
@Data
public class SchoolSubject extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "school_subject_code", type = IdType.AUTO)
    private Integer school_subject_code;

    /**
     * 
     */
    @TableField(value = "school_subject_school")
    private Integer school_subject_school;

    /**
     * 
     */
    @TableField(value = "school_subject_subject")
    private String school_subject_subject;

    /**
     * 
     */
    @TableField(value = "school_subject_best_flag")
    private Integer school_subject_best_flag;

    /**
     * 
     */
    @TableField(value = "school_subject_1_phd_flag")
    private Integer school_subject_1_phd_flag;

    /**
     * 
     */
    @TableField(value = "school_subject_1_master_flag")
    private Integer school_subject_1_master_flag;

    /**
     * 
     */
    @TableField(value = "school_subject_dual_flag")
    private Integer school_subject_dual_flag;

    /**
     * 
     */
    @TableField(value = "school_subject_rank")
    private String school_subject_rank;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}