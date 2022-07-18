package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDiscipline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDisciplineVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_discipline】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface SchoolDisciplineService extends IService<SchoolDiscipline> {
    Page<SchoolDisciplineVo> findAllByPage(Page<SchoolDisciplineVo> page, long schoolId);
    List<SchoolDisciplineVo> findAll(long schoolId);
}
