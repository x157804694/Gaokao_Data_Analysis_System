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
 * @TableName t_plan_sg
 */
@TableName(value ="t_plan_sg")
@Data
public class PlanSg extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "sg_code", type = IdType.AUTO)
    private Integer sg_code;

    /**
     * 
     */
    @TableField(value = "sg_school")
    private Integer sg_school;

    /**
     * 
     */
    @TableField(value = "sg_year")
    private String sg_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String sg_schoolName;
}