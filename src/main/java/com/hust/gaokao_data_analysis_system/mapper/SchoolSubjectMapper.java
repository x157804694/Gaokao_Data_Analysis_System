package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSubjectVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_subject】的数据库操作Mapper
* @createDate 2022-07-07 17:30:04
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject
*/
@Repository
public interface SchoolSubjectMapper extends BaseMapper<SchoolSubject> {
    Page<SchoolSubjectVo> findAllByPage(Page<SchoolSubjectVo> page, long schoolId, String disciplineId);
    List<SchoolSubjectVo> findAll(long schoolId, String disciplineId);
}




