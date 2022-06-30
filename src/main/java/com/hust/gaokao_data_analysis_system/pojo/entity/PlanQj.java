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
 * @TableName t_plan_qj
 */
@TableName(value ="t_plan_qj")
@Data
public class PlanQj extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "qj_code", type = IdType.AUTO)
    private Integer qj_code;

    /**
     * 
     */
    @TableField(value = "qj_school")
    private Integer qj_school;

    /**
     * 
     */
    @TableField(value = "qj_year")
    private String qj_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}