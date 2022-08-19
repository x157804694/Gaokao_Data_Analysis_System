package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanQj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_plan_qj】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.PlanQj
*/
@Repository
public interface PlanQjMapper extends BaseMapper<PlanQj> {
    Page<PlanQj> findAllByPage(Page<PlanQj> page, String year);
    List<PlanQj> findAllByYear(String year);
}




