package com.hust.gaokao_data_analysis_system.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class SchoolMajorVo {
    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // subjectInfo
    private Integer major_code;

    private String major_id;

    private String major_name;

    private String major_subject;

    // SchoolMajorInfo
    private Integer school_major_code;

    private String school_major_name;

    private String school_major_time;

    private String school_major_cost;

    private Integer school_major_best_flag;

    private Integer school_major_2_phd_flag;

    private Integer school_major_2_master_flag;

    private Integer school_major_rank;

}
