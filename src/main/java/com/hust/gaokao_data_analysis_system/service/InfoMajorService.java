package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoMajor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.gaokao_data_analysis_system.pojo.vo.MajorVo;

/**
* @author XDL
* @description 针对表【t_info_major】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface InfoMajorService extends IService<InfoMajor> {
    Page<MajorVo> findAllByPage(Page<MajorVo> page, String disciplineLevel, String disciplineId, String subjectId);
}
