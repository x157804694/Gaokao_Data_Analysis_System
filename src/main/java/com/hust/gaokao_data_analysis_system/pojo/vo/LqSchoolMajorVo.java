package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class LqSchoolMajorVo {
    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // SchoolClassInfo
    private Integer school_class_code;

    private String school_class_name;


    // provinceInfo
    private Integer province_code;

    private String province_name;

    // zslxInfo
    private Integer zslx_code;

    private String zslx_name;

    // pcInfo
    private Integer pc_code;

    private String pc_name;

    // LqSchoolMajorInfo
    private Integer lq_school_major_code;

    private String lq_school_major_year;

    private Integer lq_school_major_max_score;

    private Integer lq_school_major_min_score;

    private Integer lq_school_major_average_score;

    private Integer lq_school_major_high_rank;

    private Integer lq_school_major_low_rank;

    private Integer lq_school_major_p_score;


}
