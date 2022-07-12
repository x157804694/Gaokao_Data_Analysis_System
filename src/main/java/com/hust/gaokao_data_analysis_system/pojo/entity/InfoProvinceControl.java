package com.hust.gaokao_data_analysis_system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.hust.gaokao_data_analysis_system.common.BasicClass;
import lombok.Data;

/**
 * 
 * @TableName t_info_province_control
 */
@TableName(value ="t_info_province_control")
@Data
public class InfoProvinceControl extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "province_control_code", type = IdType.AUTO)
    private Integer province_control_code;

    /**
     * 
     */
    @TableField(value = "province_control_province")
    private String province_control_province;

    /**
     * 
     */
    @TableField(value = "province_control_zslx")
    private String province_control_zslx;

    /**
     * 
     */
    @TableField(value = "province_control_pc")
    private String province_control_pc;

    /**
     * 
     */
    @TableField(value = "province_control_year")
    private String province_control_year;

    /**
     * 
     */
    @TableField(value = "province_control_score")
    private Integer province_control_score;

    /**
     * 
     */
    @TableField(value = "province_control_majorScore")
    private Integer province_control_majorScore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}