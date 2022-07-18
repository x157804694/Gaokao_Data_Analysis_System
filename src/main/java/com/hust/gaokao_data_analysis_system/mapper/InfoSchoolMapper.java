package com.hust.gaokao_data_analysis_system.mapper;

import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_info_school】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool
*/
@Repository
public interface InfoSchoolMapper extends BaseMapper<InfoSchool> {
    int addBatch(List<InfoSchool> schoolList);
}




