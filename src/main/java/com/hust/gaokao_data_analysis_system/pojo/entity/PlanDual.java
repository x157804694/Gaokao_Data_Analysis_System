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
 * @TableName t_plan_dual
 */
@TableName(value ="t_plan_dual")
@Data
public class PlanDual extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "dual_code", type = IdType.AUTO)
    private Integer dual_code;

    /**
     * 
     */
    @TableField(value = "dual_school")
    private Integer dual_school;

    /**
     * 
     */
    @TableField(value = "dual_year")
    private String dual_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String dual_schoolName;
}