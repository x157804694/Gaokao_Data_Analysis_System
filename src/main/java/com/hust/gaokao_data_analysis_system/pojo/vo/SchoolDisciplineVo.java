package com.hust.gaokao_data_analysis_system.pojo.vo;

import lombok.Data;

@Data
public class SchoolDisciplineVo {

    private Integer school_code;

    private Integer school_id;

    private String school_name;

    private Integer discipline_code;

    private String discipline_id;

    private String discipline_name;

    private String discipline_level;

    private Integer school_discipline_code;
}
