package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDualSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDualSubjectVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSubjectVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_dual_subject】的数据库操作Service
* @createDate 2022-07-29 10:27:09
*/
public interface SchoolDualSubjectService extends IService<SchoolDualSubject> {
    Page<SchoolDualSubjectVo> findAllByPage(Page<SchoolSubjectVo> page, long schoolId, String disciplineId, String year);
    List<SchoolDualSubjectVo> findAll(long schoolId, String year);
    List<String> getYearsBySchool(long schoolId);
}
