package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSgMajor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSgMajorVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_sg_major】的数据库操作Service
* @createDate 2022-08-01 10:06:27
*/
public interface SchoolSgMajorService extends IService<SchoolSgMajor> {
    Page<SchoolSgMajorVo> findAllByPage(Page<SchoolSgMajorVo> page, long schoolId, String disciplineId, String subjectId, String year);
    List<SchoolSgMajorVo> findAll(long schoolId, String year);
    List<String> getYearsBySchool(long schoolId);
}
