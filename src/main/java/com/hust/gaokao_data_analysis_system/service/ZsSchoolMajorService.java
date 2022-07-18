package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.ZsSchoolMajor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.ZsSchoolMajorVo;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_zs_school_major】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface ZsSchoolMajorService extends IService<ZsSchoolMajor> {
    Page<ZsSchoolMajorVo> findAllByPage(Page<ZsSchoolMajorVo> page,int schoolId,int provinceCode,String year,int zslxCode,int pcCode);
    List<ZsSchoolMajorVo> findAll(long schoolId);
}
