package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.SchoolMajorService;
import com.hust.gaokao_data_analysis_system.mapper.SchoolMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_major】的数据库操作Service实现
* @createDate 2022-07-08 00:44:50
*/
@Service
public class SchoolMajorServiceImpl extends ServiceImpl<SchoolMajorMapper, SchoolMajor>
    implements SchoolMajorService{
    private SchoolMajorMapper schoolMajorMapper;

    @Autowired
    public void setSchoolMajorMapper(SchoolMajorMapper schoolMajorMapper){
        this.schoolMajorMapper = schoolMajorMapper;
    }

    @Override
    public Page<SchoolMajorVo> findAllByPage(Page<SchoolMajorVo> page) {
        return schoolMajorMapper.findAllByPage(page);
    }

    @Override
    public List<SchoolMajorVo> findAll(long schoolId) {
        return schoolMajorMapper.findAll(schoolId);
    }
}




