package com.hust.gaokao_data_analysis_system.pojo.dto;

import com.hust.gaokao_data_analysis_system.common.PageRequest;
import lombok.Data;

@Data
public class ZsOrLQSchoolMajorDTO extends PageRequest {
    private Integer school_id;
    private String year;
    private Integer province_code;
    private Integer zslx_code;
    private Integer pc_code;
}
