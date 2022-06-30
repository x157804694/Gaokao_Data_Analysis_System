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
 * @TableName t_rank_qs
 */
@TableName(value ="t_rank_qs")
@Data
public class RankQs extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "qs_code", type = IdType.AUTO)
    private Integer qs_code;

    /**
     * 
     */
    @TableField(value = "qs_school")
    private Integer qs_school;

    /**
     * 
     */
    @TableField(value = "qs_rank")
    private Integer qs_rank;

    /**
     * 
     */
    @TableField(value = "qs_year")
    private String qs_year;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}