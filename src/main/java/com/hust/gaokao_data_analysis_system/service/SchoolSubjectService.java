package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSubjectVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_subject】的数据库操作Service
* @createDate 2022-07-07 17:30:04
*/
public interface SchoolSubjectService extends IService<SchoolSubject> {
    Page<SchoolSubjectVo> findAllByPage(Page<SchoolSubjectVo> page, long schoolId, String disciplineId);
    List<SchoolSubjectVo> findAll(long schoolId, String disciplineId);
}
