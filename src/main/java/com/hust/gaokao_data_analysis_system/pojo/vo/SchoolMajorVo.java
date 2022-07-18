package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class SchoolMajorVo {
    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // MajorInfo
    private Integer major_code;

    private String major_id;

    private String major_name;

    // SubjectInfo
    private Integer subject_code;

    private String subject_id;

    private String subject_name;

    // DisciplineInfo
    private Integer discipline_code;

    private String discipline_id;

    private String discipline_name;

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
