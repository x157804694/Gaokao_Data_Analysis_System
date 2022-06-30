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
 * @TableName t_geo_city
 */
@TableName(value ="t_geo_city")
@Data
public class GeoCity extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "city_code", type = IdType.AUTO)
    private Integer city_code;

    /**
     * 
     */
    @TableField(value = "city_name")
    private String city_name;

    /**
     * 
     */
    @TableField(value = "city_province")
    private Integer city_province;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}