package com.hust.gaokao_data_analysis_system.pojo.dto;

import com.hust.gaokao_data_analysis_system.common.PageRequest;
import lombok.Data;

@Data
public class RankDTO extends PageRequest {
    private String rk_year;
    private String qs_year;
    private String tws_year;
    private String us_year;
    private String wsl_year;
    private String xyh_year;
}
