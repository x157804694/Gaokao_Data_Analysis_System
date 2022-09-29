package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class ZsSchoolMajorVo {
    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // SchoolMajorInfo
    private Integer school_major_code;

    private String school_major_name;

    private String school_major_time;

    private String school_major_cost;

    private Integer school_major_best_flag;

    private Integer school_major_2_phd_flag;

    private Integer school_major_2_master_flag;

    private Integer school_major_rank;

    // provinceInfo
    private Integer province_code;

    private String province_name;

    // zslxInfo
    private Integer zslx_code;

    private String zslx_name;

    // pcInfo
    private Integer pc_code;

    private String pc_name;

    // ZsSchoolMajorInfo
    private Integer zs_school_major_code;

    private String zs_school_major_year;

    private Integer zs_school_major_number;

    private Integer zs_school_major_qj_flag;
}
