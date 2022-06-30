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
 * @TableName t_info_discipline
 */
@TableName(value ="t_info_discipline")
@Data
public class InfoDiscipline extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "discipline_code", type = IdType.AUTO)
    private Integer discipline_code;

    /**
     * 
     */
    @TableField(value = "discipline_name")
    private String discipline_name;

    /**
     * 
     */
    @TableField(value = "discipline_level")
    private String discipline_level;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}