package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.MajorVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolClassVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SubjectVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_class】的数据库操作Service
* @createDate 2022-09-28 15:12:53
*/
public interface SchoolClassService extends IService<SchoolClass> {
    Page<SchoolClassVo> findAllByPage(Page<SchoolClassVo> page, long schoolId);
    List<SchoolClassVo> findAll(long schoolId);
//    List<SubjectVo> findAllSubjectsBySchool(long SchoolId);
}
