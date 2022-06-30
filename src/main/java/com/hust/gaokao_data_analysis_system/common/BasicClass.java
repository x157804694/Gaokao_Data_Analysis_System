package com.hust.gaokao_data_analysis_system.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
@Data
public class BasicClass {
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date create_time;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
}
