package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolClass;
import com.hust.gaokao_data_analysis_system.pojo.vo.MajorVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolClassVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SubjectVo;
import com.hust.gaokao_data_analysis_system.service.SchoolClassService;
import com.hust.gaokao_data_analysis_system.mapper.SchoolClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDL
 * @description 针对表【t_school_class】的数据库操作Service实现
 * @createDate 2022-09-28 15:12:53
 */
@Service
public class SchoolClassServiceImpl extends ServiceImpl<SchoolClassMapper, SchoolClass>
        implements SchoolClassService {

    private SchoolClassMapper schoolClassMapper;

    @Autowired
    public void setSchoolClassMapper(SchoolClassMapper schoolClassMapper) {
        this.schoolClassMapper = schoolClassMapper;
    }

    @Override
    public Page<SchoolClassVo> findAllByPage(Page<SchoolClassVo> page, long schoolId) {
        return schoolClassMapper.findAllByPage(page,schoolId);
    }

    @Override
    public List<SchoolClassVo> findAll(long schoolId) {
        return schoolClassMapper.findAll(schoolId);
    }

//    @Override
//    public List<SubjectVo> findAllSubjectsBySchool(long schoolId) {
//        return schoolClassMapper.findAllSubjectsBySchool(schoolId);
//    }
}




