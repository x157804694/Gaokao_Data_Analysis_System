package com.hust.gaokao_data_analysis_system.pojo.dto;

import com.hust.gaokao_data_analysis_system.common.PageRequest;
import lombok.Data;

@Data
public class InfoProvinceControlDTO extends PageRequest {
    private String province_control_province;
    private String province_control_zslx;
    private String province_control_pc;
    private String province_control_year;
    private int province_control_score;
    private int province_control_majorScore;
}
