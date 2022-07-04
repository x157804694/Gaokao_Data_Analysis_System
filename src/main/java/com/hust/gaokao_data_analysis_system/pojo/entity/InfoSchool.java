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
 * @TableName t_info_school
 */
@TableName(value = "t_info_school")
@Data
public class InfoSchool extends BasicClass implements Serializable {

    @TableId(value = "school_code", type = IdType.AUTO)
    private Integer school_code;

    @TableField(value = "school_id")
    private Integer school_id;

    @TableField(value = "school_name")
    private String school_name;

    @TableField(value = "school_level")
    private String school_level;

    @TableField(value = "school_type")
    private String school_type;

    @TableField(value = "school_nature")
    private String school_nature;

    @TableField(value = "school_region")
    private String school_region;

    @TableField(value = "school_province")
    private String school_province;

    @TableField(value = "school_city")
    private String school_city;

    @TableField(value = "school_211")
    private Integer school_211;

    @TableField(value = "school_985")
    private Integer school_985;

    @TableField(value = "school_belong")
    private String school_belong;

    @TableField(value = "school_rk")
    private Integer school_rk;

    @TableField(value = "school_xyh")
    private Integer school_xyh;

    @TableField(value = "school_wsl")
    private Integer school_wsl;

    @TableField(value = "school_qs")
    private Integer school_qs;

    @TableField(value = "school_us")
    private Integer school_us;

    @TableField(value = "school_tws")
    private Integer school_tws;

    @TableField(value = "school_requirments")
    private String school_requirments;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(value = "school_dual")
    private int school_dual;

    @TableField(value = "school_qj")
    private int school_qj;

    @TableField(value = "school_sg")
    private int school_sg;
}