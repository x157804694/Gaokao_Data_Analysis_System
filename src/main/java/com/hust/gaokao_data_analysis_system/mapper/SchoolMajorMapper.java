package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_school_major】的数据库操作Mapper
* @createDate 2022-07-08 00:44:50
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajor
*/
@Repository
public interface SchoolMajorMapper extends BaseMapper<SchoolMajor> {
    Page<SchoolMajorVo> findAllByPage(Page<SchoolMajorVo> page);
    List<SchoolMajorVo> findAll(long SchoolId);
}




