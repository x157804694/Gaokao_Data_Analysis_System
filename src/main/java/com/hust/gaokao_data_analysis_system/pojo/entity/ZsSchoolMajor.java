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
 * @TableName t_zs_school_major
 */
@TableName(value ="t_zs_school_major")
@Data
public class ZsSchoolMajor extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "zs_school_major_code", type = IdType.AUTO)
    private Integer zs_school_major_code;

    /**
     * 
     */
    @TableField(value = "zs_school_major_school")
    private Integer zs_school_major_school;

    /**
     * 
     */
    @TableField(value = "zs_school_major_major")
    private Integer zs_school_major_major;

    /**
     * 
     */
    @TableField(value = "zs_school_major_year")
    private String zs_school_major_year;

    /**
     * 
     */
    @TableField(value = "zs_school_major_number")
    private Integer zs_school_major_number;

    /**
     * 
     */
    @TableField(value = "zs_school_major_qj_flag")
    private Integer zs_school_major_qj_flag;

    /**
     * 
     */
    @TableField(value = "zs_school_major_province")
    private Integer zs_school_major_province;

    /**
     * 
     */
    @TableField(value = "zs_school_major_zslx")
    private Integer zs_school_major_zslx;

    /**
     * 
     */
    @TableField(value = "zs_school_major_pc")
    private Integer zs_school_major_pc;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}