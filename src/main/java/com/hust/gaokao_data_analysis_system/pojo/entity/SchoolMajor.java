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
 * @TableName t_school_major
 */
@TableName(value ="t_school_major")
@Data
public class SchoolMajor extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "school_major_code", type = IdType.AUTO)
    private Integer school_major_code;

    /**
     * 
     */
    @TableField(value = "school_major_school")
    private Integer school_major_school;

    /**
     * 
     */
    @TableField(value = "school_major_major")
    private String school_major_major;

    /**
     * 
     */
    @TableField(value = "school_major_time")
    private String school_major_time;

    /**
     * 
     */
    @TableField(value = "school_major_cost")
    private String school_major_cost;

    /**
     * 
     */
    @TableField(value = "school_major_2_phd_flag")
    private Integer school_major_2_phd_flag;

    /**
     * 
     */
    @TableField(value = "school_major_master_flag")
    private Integer school_major_master_flag;

    /**
     * 
     */
    @TableField(value = "school_major_rank")
    private Integer school_major_rank;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}