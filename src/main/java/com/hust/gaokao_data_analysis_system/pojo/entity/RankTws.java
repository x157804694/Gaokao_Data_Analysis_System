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
 * @TableName t_rank_tws
 */
@TableName(value ="t_rank_tws")
@Data
public class RankTws extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "tws_code", type = IdType.AUTO)
    private Integer tws_code;

    /**
     * 
     */
    @TableField(value = "tws_school")
    private Integer tws_school;

    /**
     * 
     */
    @TableField(value = "tws_rank")
    private Integer tws_rank;

    /**
     * 
     */
    @TableField(value = "tws_year")
    private String tws_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}