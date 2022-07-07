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
 * @TableName t_school_discipline
 */
@TableName(value ="t_school_discipline")
@Data
public class SchoolDiscipline extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "school_discipline_code", type = IdType.AUTO)
    private Integer school_discipline_code;

    /**
     * 
     */
    @TableField(value = "school_discipline_school")
    private Integer school_discipline_school;

    /**
     * 
     */
    @TableField(value = "school_discipline_discipline")
    private String school_discipline_discipline;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}