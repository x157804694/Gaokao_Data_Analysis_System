package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolQjMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolQjMajorVo;
import com.hust.gaokao_data_analysis_system.service.SchoolQjMajorService;
import com.hust.gaokao_data_analysis_system.mapper.SchoolQjMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_qj_major】的数据库操作Service实现
* @createDate 2022-08-01 10:06:27
*/
@Service
public class SchoolQjMajorServiceImpl extends ServiceImpl<SchoolQjMajorMapper, SchoolQjMajor>
    implements SchoolQjMajorService{
    private SchoolQjMajorMapper schoolQjMajorMapper;

    @Autowired
    public void setSchoolQjMajorMapper(SchoolQjMajorMapper schoolQjMajorMapper){
        this.schoolQjMajorMapper = schoolQjMajorMapper;
    }
    @Override
    public Page<SchoolQjMajorVo> findAllByPage(Page<SchoolQjMajorVo> page, long schoolId, String disciplineId, String subjectId, String year) {
        return schoolQjMajorMapper.findAllByPage(page,schoolId,disciplineId,subjectId,year);
    }

    @Override
    public List<SchoolQjMajorVo> findAll(long schoolId, String year) {
        return schoolQjMajorMapper.findAll(schoolId,year);
    }

    @Override
    public List<String> getYearsBySchool(long schoolId) {
        return schoolQjMajorMapper.getYearsBySchool(schoolId);
    }
}




