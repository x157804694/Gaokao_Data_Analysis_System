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
 * @TableName t_rank_us
 */
@TableName(value ="t_rank_us")
@Data
public class RankUs extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "us_code", type = IdType.AUTO)
    private Integer us_code;

    /**
     * 
     */
    @TableField(value = "us_school")
    private Integer us_school;

    /**
     * 
     */
    @TableField(value = "us_rank")
    private Integer us_rank;

    /**
     * 
     */
    @TableField(value = "us_year")
    private String us_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String us_schoolName;
}