package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanDual;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_plan_dual】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.PlanDual
*/
@Repository
public interface PlanDualMapper extends BaseMapper<PlanDual> {
    Page<PlanDual> findAllByPage(Page<PlanDual> page, String year);
    List<PlanDual> findAllByYear(String year);
}




