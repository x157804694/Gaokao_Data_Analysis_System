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
 * @TableName t_lq_school_major
 */
@TableName(value ="t_lq_school_major")
@Data
public class LqSchoolMajor extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "lq_school_major_code", type = IdType.AUTO)
    private Integer lq_school_major_code;

    /**
     * 
     */
    @TableField(value = "lq_school_major_school")
    private Integer lq_school_major_school;

    /**
     * 
     */
    @TableField(value = "lq_school_major_class")
    private Integer lq_school_major_class;

    /**
     * 
     */
    @TableField(value = "lq_school_major_year")
    private String lq_school_major_year;

    /**
     * 
     */
    @TableField(value = "lq_school_major_province")
    private Integer lq_school_major_province;

    /**
     * 
     */
    @TableField(value = "lq_school_major_max_score")
    private Integer lq_school_major_max_score;

    /**
     * 
     */
    @TableField(value = "lq_school_major_min_score")
    private Integer lq_school_major_min_score;

    /**
     * 
     */
    @TableField(value = "lq_school_major_high_rank")
    private Integer lq_school_major_high_rank;

    /**
     * 
     */
    @TableField(value = "lq_school_major_low_rank")
    private Integer lq_school_major_low_rank;

    /**
     * 
     */
    @TableField(value = "lq_school_major_p_score")
    private Integer lq_school_major_p_score;

    /**
     * 
     */
    @TableField(value = "lq_school_major_average_score")
    private Integer lq_school_major_average_score;

    /**
     * 
     */
    @TableField(value = "lq_school_major_zslx")
    private Integer lq_school_major_zslx;

    /**
     * 
     */
    @TableField(value = "lq_school_major_pc")
    private Integer lq_school_major_pc;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}