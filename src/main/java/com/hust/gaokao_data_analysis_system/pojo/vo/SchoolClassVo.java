package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class SchoolClassVo {

    // SchoolClassInfo
    private Integer school_class_code;

    private String school_class_name;

    // schoolInfo
    private Integer school_code;

    private Integer school_id;

    private String school_name;

    // MajorInfo
    private List<SubjectVo> subjectList;
}
