package com.hust.gaokao_data_analysis_system.pojo.dto;

import com.hust.gaokao_data_analysis_system.common.PageRequest;
import lombok.Data;

@Data
public class SchoolSgMajorDTO extends PageRequest {
    private Integer school_id;
    private String discipline_id;
    private String subject_id;
    private String year;
}
