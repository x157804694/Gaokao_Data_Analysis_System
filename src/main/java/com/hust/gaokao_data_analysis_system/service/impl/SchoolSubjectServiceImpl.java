package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSubjectVo;
import com.hust.gaokao_data_analysis_system.service.SchoolSubjectService;
import com.hust.gaokao_data_analysis_system.mapper.SchoolSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_subject】的数据库操作Service实现
* @createDate 2022-07-07 17:30:04
*/
@Service
public class SchoolSubjectServiceImpl extends ServiceImpl<SchoolSubjectMapper, SchoolSubject>
    implements SchoolSubjectService{

    private SchoolSubjectMapper schoolSubjectMapper;

    @Autowired
    public void setSchoolSubjectMapper(SchoolSubjectMapper schoolSubjectMapper){
        this.schoolSubjectMapper = schoolSubjectMapper;
    }

    @Override
    public Page<SchoolSubjectVo> findAllByPage(Page<SchoolSubjectVo> page) {
        return schoolSubjectMapper.findAllByPage(page);
    }

    @Override
    public List<SchoolSubjectVo> findAll(long schoolId) {
        return schoolSubjectMapper.findAll(schoolId);
    }
}




