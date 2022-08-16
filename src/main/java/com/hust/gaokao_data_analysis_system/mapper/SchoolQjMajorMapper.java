package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolQjMajor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDualSubjectVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolQjMajorVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_qj_major】的数据库操作Mapper
* @createDate 2022-08-01 10:06:27
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.SchoolQjMajor
*/
@Repository
public interface SchoolQjMajorMapper extends BaseMapper<SchoolQjMajor> {
    Page<SchoolQjMajorVo> findAllByPage(Page<SchoolQjMajorVo> page, long schoolId, String disciplineId, String subjectId,String year);
    List<SchoolQjMajorVo> findAll(long schoolId, String year);
    List<String> getYearsBySchool(long schoolId);
}




