package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class SchoolSgMajorVo {
    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // disciplineInfo
    private String discipline_code;

    private String discipline_id;

    private String discipline_name;

    // subjectInfo
    private Integer subject_code;

    private String subject_id;

    private String subject_name;

    // MajorInfo
    private Integer major_code;

    private String major_id;

    private String major_name;

    // qjMajorInfo
    private Integer school_sg_major_code;

    private String school_sg_major_major;

    private String school_sg_major_year;
}
