package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class SchoolSubjectVo {
    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // subjectInfo
    private Integer subject_code;

    private String subject_id;

    private String subject_name;

    private String subject_discipline;

    // schoolSubjectInfo
    private Integer school_subject_code;

    private Integer school_subject_best_flag;

    private Integer school_subject_1_phd_flag;

    private Integer school_subject_1_master_flag;

    private Integer school_subject_dual_flag;

    private String school_subject_rank;
}
