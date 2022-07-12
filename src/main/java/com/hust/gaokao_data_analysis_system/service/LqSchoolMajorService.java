package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.LqSchoolMajor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.LqSchoolMajorVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_lq_school_major】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface LqSchoolMajorService extends IService<LqSchoolMajor> {
    Page<LqSchoolMajorVo> findAllByPage(Page<LqSchoolMajorVo> page);
    List<LqSchoolMajorVo> findAll(long schoolId);
}
