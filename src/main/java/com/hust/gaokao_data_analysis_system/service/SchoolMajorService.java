package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_major】的数据库操作Service
* @createDate 2022-07-08 00:44:50
*/
public interface SchoolMajorService extends IService<SchoolMajor> {
    Page<SchoolMajorVo> findAllByPage(Page<SchoolMajorVo> page, long schoolId, String disciplineId, String subjectId);
    List<SchoolMajorVo> findAll(long schoolId);
}
