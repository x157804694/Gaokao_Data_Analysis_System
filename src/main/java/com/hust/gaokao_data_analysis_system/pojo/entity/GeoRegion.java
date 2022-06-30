package com.hust.gaokao_data_analysis_system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.hust.gaokao_data_analysis_system.common.BasicClass;
import lombok.Data;

/**
 * 
 * @TableName t_geo_region
 */
@TableName(value ="t_geo_region")
@Data
public class GeoRegion extends BasicClass implements Serializable {
    /**
     * 
     */
    @TableId(value = "region_code", type = IdType.AUTO)
    private Integer region_code;

    /**
     * 
     */
    @TableField(value = "region_name")
    private String region_name;

    /**
     * 
     */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}