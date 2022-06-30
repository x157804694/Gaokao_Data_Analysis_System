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
 * @TableName t_rank_wsl
 */
@TableName(value ="t_rank_wsl")
@Data
public class RankWsl extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "wsl_code", type = IdType.AUTO)
    private Integer wsl_code;

    /**
     * 
     */
    @TableField(value = "wsl_school")
    private Integer wsl_school;

    /**
     * 
     */
    @TableField(value = "wsl_rank")
    private Integer wsl_rank;

    /**
     * 
     */
    @TableField(value = "wsl_year")
    private String wsl_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}