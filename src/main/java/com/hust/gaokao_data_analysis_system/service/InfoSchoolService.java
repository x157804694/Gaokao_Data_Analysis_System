package com.hust.gaokao_data_analysis_system.service;

import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_info_school】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface InfoSchoolService extends IService<InfoSchool> {
    int addBatch(List<InfoSchool> schoolList);
}
