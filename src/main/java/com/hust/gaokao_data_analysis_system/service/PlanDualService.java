package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanDual;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author XDL
* @description 针对表【t_plan_dual】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface PlanDualService extends IService<PlanDual> {
    Page<PlanDual> findAll(Page<PlanDual> page, String year);
}
