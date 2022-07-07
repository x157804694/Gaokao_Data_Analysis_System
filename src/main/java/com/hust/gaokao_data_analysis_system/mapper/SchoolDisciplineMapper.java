package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDiscipline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDisciplineVo;
import org.springframework.stereotype.Repository;

/**
* @author XDL
* @description 针对表【t_school_discipline】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDiscipline
*/
@Repository
public interface SchoolDisciplineMapper extends BaseMapper<SchoolDiscipline> {
    Page<SchoolDisciplineVo> findAll(Page<SchoolDisciplineVo> page);
}




