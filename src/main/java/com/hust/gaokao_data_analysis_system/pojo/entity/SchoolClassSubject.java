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
 * @TableName t_school_class_subject
 */
@TableName(value ="t_school_class_subject")
@Data
public class SchoolClassSubject extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "schoolClass_subject_code", type = IdType.AUTO)
    private Integer schoolClass_subject_code;

    /**
     * 
     */
    @TableField(value = "schoolClass_subject_class")
    private Integer schoolClass_subject_class;

    /**
     * 
     */
    @TableField(value = "schoolClass_subject_subject")
    private String schoolClass_subject_subject;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}