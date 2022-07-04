package com.hust.gaokao_data_analysis_system.pojo.dto;

import com.hust.gaokao_data_analysis_system.common.PageRequest;
import lombok.Data;

@Data
public class SchoolInfoQueryDTO extends PageRequest {
    private String school_name;
    private String school_level;
    private String school_type;
    private String school_nature;
    private String school_region;
    private String school_province;
    private String school_city;
    private int school_211;
    private int school_985;
    private String school_belong;
    private int school_dual;
    private int school_qj;
    private int school_sg;
}
