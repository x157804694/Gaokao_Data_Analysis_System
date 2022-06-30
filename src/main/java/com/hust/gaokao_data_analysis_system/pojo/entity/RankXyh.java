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
 * @TableName t_rank_xyh
 */
@TableName(value ="t_rank_xyh")
@Data
public class RankXyh extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "xyh_code", type = IdType.AUTO)
    private Integer xyh_code;

    /**
     * 
     */
    @TableField(value = "xyh_school")
    private Integer xyh_school;

    /**
     * 
     */
    @TableField(value = "xyh_rank")
    private Integer xyh_rank;

    /**
     * 
     */
    @TableField(value = "xyh_year")
    private String xyh_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}