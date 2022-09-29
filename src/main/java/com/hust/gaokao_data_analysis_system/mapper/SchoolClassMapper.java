package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.MajorVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolClassVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SubjectVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_class】的数据库操作Mapper
* @createDate 2022-09-28 15:12:53
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.SchoolClass
*/
@Repository
public interface SchoolClassMapper extends BaseMapper<SchoolClass> {
    Page<SchoolClassVo> findAllByPage(Page<SchoolClassVo> page, long schoolId);
    List<SchoolClassVo> findAll(long SchoolId);
    List<SubjectVo> findAllSubjectsBySchool(long SchoolId);
}




