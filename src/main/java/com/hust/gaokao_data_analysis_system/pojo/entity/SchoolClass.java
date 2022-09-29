package com.hust.gaokao_data_analysis_system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hust.gaokao_data_analysis_system.common.BasicClass;
import lombok.Data;

/**
 * 
 * @TableName t_school_class
 */
@TableName(value ="t_school_class")
@Data
public class SchoolClass extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "school_class_code", type = IdType.AUTO)
    private Integer school_class_code;

    /**
     * 
     */
    @TableField(value = "school_class_school")
    private Integer school_class_school;

    /**
     * 
     */
    @TableField(value = "school_class_name")
    private String school_class_name;

    @TableField(exist = false)
    private List<String> subjectIdList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}