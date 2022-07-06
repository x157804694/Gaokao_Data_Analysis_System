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
 * @TableName t_info_subject
 */
@TableName(value ="t_info_subject")
@Data
public class InfoSubject extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "subject_code", type = IdType.AUTO)
    private Integer subject_code;

    @TableField(value = "subject_id")
    private String subject_id;
    /**
     * 
     */
    @TableField(value = "subject_name")
    private String subject_name;

    /**
     * 
     */
    @TableField(value = "subject_discipline")
    private String subject_discipline;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}