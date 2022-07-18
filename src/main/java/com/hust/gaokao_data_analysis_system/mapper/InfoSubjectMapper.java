package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.SubjectVo;
import org.springframework.stereotype.Repository;

/**
* @author XDL
* @description 针对表【t_info_subject】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.InfoSubject
*/
@Repository
public interface InfoSubjectMapper extends BaseMapper<InfoSubject> {
    Page<SubjectVo> findAllByPage(Page<SubjectVo> page, String disciplineId, String disciplineLevel);
}




