package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDualSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDisciplineVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDualSubjectVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSubjectVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_dual_subject】的数据库操作Mapper
* @createDate 2022-07-29 10:27:09
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDualSubject
*/
@Repository
public interface SchoolDualSubjectMapper extends BaseMapper<SchoolDualSubject> {
    Page<SchoolDualSubjectVo> findAllByPage(Page<SchoolSubjectVo> page, long schoolId, String disciplineId, String year);
    List<SchoolDualSubjectVo> findAll(long schoolId, String year);
    List<String> getYearsBySchool(long schoolId);
}




