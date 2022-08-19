package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanQj;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_plan_qj】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface PlanQjService extends IService<PlanQj> {
    Page<PlanQj> findAllByPage(Page<PlanQj> page, String year);
    List<PlanQj> findAllByYear(String year);
}
