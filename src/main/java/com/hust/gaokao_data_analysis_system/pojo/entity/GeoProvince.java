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
 * @TableName t_geo_province
 */
@TableName(value ="t_geo_province")
@Data
public class GeoProvince extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "province_code", type = IdType.AUTO)
    private Integer province_code;

    /**
     * 
     */
    @TableField(value = "province_name")
    private String province_name;

    /**
     * 
     */
    @TableField(value = "province_region")
    private Integer province_region;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}