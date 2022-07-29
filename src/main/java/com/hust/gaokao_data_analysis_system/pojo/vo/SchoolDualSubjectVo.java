package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class SchoolDualSubjectVo {
    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // subjectInfo
    private Integer subject_code;

    private String subject_id;

    private String subject_name;

    // disciplineInfo
    private String discipline_code;

    private String discipline_id;

    private String discipline_name;

    // dualSubjectInfo
    private Integer school_dual_subject_code;

    private Integer school_dual_subject_school_subject;

    private String school_dual_subject_year;

}
