package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class MajorVo {
    // MajorInfo
    private Integer major_code;

    private String major_id;

    private String major_name;

    // subjectInfo
    private Integer subject_code;

    private String subject_id;

    private String subject_name;

    // DisciplineInfo
    private Integer discipline_code;

    private String discipline_id;

    private String discipline_name;

    private String discipline_level;
}
