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
 * @TableName t_school_major_major
 */
@TableName(value ="t_school_major_major")
@Data
public class SchoolMajorMajor extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "schoolMajor_major_code", type = IdType.AUTO)
    private Integer schoolMajor_major_code;

    /**
     * 
     */
    @TableField(value = "schoolMajor_major_schoolMajor")
    private Integer schoolMajor_major_schoolMajor;

    /**
     * 
     */
    @TableField(value = "schoolMajor_major_major")
    private String schoolMajor_major_major;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}