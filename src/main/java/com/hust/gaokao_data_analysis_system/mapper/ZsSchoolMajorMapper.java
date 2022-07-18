package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.ZsSchoolMajor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.gaokao_data_analysis_system.pojo.vo.ZsSchoolMajorVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_zs_school_major】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.ZsSchoolMajor
*/
@Repository
public interface ZsSchoolMajorMapper extends BaseMapper<ZsSchoolMajor> {
    Page<ZsSchoolMajorVo> findAllByPage(Page<ZsSchoolMajorVo> page,int schoolId,int provinceCode,String year,int zslxCode,int pcCode);
    List<ZsSchoolMajorVo> findAll(long schoolId);
}




