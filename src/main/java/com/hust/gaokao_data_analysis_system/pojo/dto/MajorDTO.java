package com.hust.gaokao_data_analysis_system.pojo.dto;

import com.hust.gaokao_data_analysis_system.common.PageRequest;
import lombok.Data;

@Data
public class MajorDTO extends PageRequest {
    private String discipline_level;
    private String discipline_id;
    private String subject_id;
}
