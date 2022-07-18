package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class SubjectVo {
    // subjectInfo
    private Integer subject_code;

    private String subject_id;

    private String subject_name;

    // disciplineInfo
    private Integer discipline_code;

    private String discipline_id;

    private String discipline_name;

    private String discipline_level;
}
