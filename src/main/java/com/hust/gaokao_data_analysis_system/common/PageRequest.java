package com.hust.gaokao_data_analysis_system.common;

import lombok.Data;

@Data
public class PageRequest {
    private int currentPage;
    private int pageSize;
    private String[] orders;
}
