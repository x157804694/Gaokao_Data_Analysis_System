package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSgMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSgMajorVo;
import com.hust.gaokao_data_analysis_system.service.SchoolSgMajorService;
import com.hust.gaokao_data_analysis_system.mapper.SchoolSgMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDL
 * @description 针对表【t_school_sg_major】的数据库操作Service实现
 * @createDate 2022-08-01 10:06:27
 */
@Service
public class SchoolSgMajorServiceImpl extends ServiceImpl<SchoolSgMajorMapper, SchoolSgMajor>
        implements SchoolSgMajorService {
    private SchoolSgMajorMapper schoolSgMajorMapper;

    @Autowired
    public void setSchoolSgMajorMapper(SchoolSgMajorMapper schoolSgMajorMapper) {
        this.schoolSgMajorMapper = schoolSgMajorMapper;
    }

    @Override
    public Page<SchoolSgMajorVo> findAllByPage(Page<SchoolSgMajorVo> page, long schoolId, String disciplineId, String subjectId, String year) {
        return schoolSgMajorMapper.findAllByPage(page,schoolId,disciplineId,subjectId,year);
    }

    @Override
    public List<SchoolSgMajorVo> findAll(long schoolId, String year) {
        return schoolSgMajorMapper.findAll(schoolId,year);
    }

    @Override
    public List<String> getYearsBySchool(long schoolId) {
        return schoolSgMajorMapper.getYearsBySchool(schoolId);
    }
}




