package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDualSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDualSubjectVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSubjectVo;
import com.hust.gaokao_data_analysis_system.service.SchoolDualSubjectService;
import com.hust.gaokao_data_analysis_system.mapper.SchoolDualSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_dual_subject】的数据库操作Service实现
* @createDate 2022-07-29 10:27:09
*/
@Service
public class SchoolDualSubjectServiceImpl extends ServiceImpl<SchoolDualSubjectMapper, SchoolDualSubject>
    implements SchoolDualSubjectService{

    private SchoolDualSubjectMapper schoolDualSubjectMapper;

    @Autowired
    public void setSchoolDualSubjectMapper(SchoolDualSubjectMapper schoolDualSubjectMapper){
        this.schoolDualSubjectMapper = schoolDualSubjectMapper;
    }

    @Override
    public Page<SchoolDualSubjectVo> findAllByPage(Page<SchoolSubjectVo> page, long schoolId, String disciplineId, String year) {
        return schoolDualSubjectMapper.findAllByPage(page,schoolId,disciplineId,year);
    }

    @Override
    public List<SchoolDualSubjectVo> findAll(long schoolId,  String year) {
        return schoolDualSubjectMapper.findAll(schoolId, year);
    }

    @Override
    public List<String> getYearsBySchool(long schoolId) {
        return schoolDualSubjectMapper.getYearsBySchool(schoolId);
    }
}




