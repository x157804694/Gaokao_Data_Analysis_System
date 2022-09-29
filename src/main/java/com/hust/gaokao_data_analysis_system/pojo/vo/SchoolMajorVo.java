package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class SchoolMajorVo {
    // SchoolMajorInfo
    private Integer school_major_code;

    private Integer school_major_rank;

    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // DisciplineInfo
    private Integer discipline_code;

    private String discipline_id;

    private String discipline_name;

    // SubjectInfo
    private Integer subject_code;

    private String subject_id;

    private String subject_name;

    // MajorInfo
    private Integer major_code;

    private String major_id;

    private String major_name;
}
