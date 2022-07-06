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
 * @TableName t_rank_rk
 */
@TableName(value ="t_rank_rk")
@Data
public class RankRk extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "rk_code", type = IdType.AUTO)
    private Integer rk_code;

    /**
     * 
     */
    @TableField(value = "rk_school")
    private Integer rk_school;

    /**
     * 
     */
    @TableField(value = "rk_rank")
    private Integer rk_rank;

    /**
     * 
     */
    @TableField(value = "rk_year")
    private String rk_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String rk_schoolName;
}