package com.hust.gaokao_data_analysis_system.pojo.dto;

import com.hust.gaokao_data_analysis_system.common.PageRequest;
import lombok.Data;

@Data
public class PlanDTO extends PageRequest {
    private String dual_year;
    private String qj_year;
    private String sg_year;
}
