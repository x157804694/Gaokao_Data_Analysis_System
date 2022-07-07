package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDiscipline;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDisciplineVo;
import com.hust.gaokao_data_analysis_system.service.SchoolDisciplineService;
import com.hust.gaokao_data_analysis_system.mapper.SchoolDisciplineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_school_discipline】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class SchoolDisciplineServiceImpl extends ServiceImpl<SchoolDisciplineMapper, SchoolDiscipline>
    implements SchoolDisciplineService{

    private SchoolDisciplineMapper schoolDisciplineMapper;

    @Autowired
    public void setSchoolDisciplineMapper(SchoolDisciplineMapper schoolDisciplineMapper){
        this.schoolDisciplineMapper = schoolDisciplineMapper;
    }

    @Override
    public Page<SchoolDisciplineVo> findAll(Page<SchoolDisciplineVo> page) {
        return schoolDisciplineMapper.findAll(page);
    }
}




