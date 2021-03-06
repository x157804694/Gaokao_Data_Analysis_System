package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanSg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author XDL
* @description 针对表【t_plan_sg】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.PlanSg
*/
@Repository
public interface PlanSgMapper extends BaseMapper<PlanSg> {
    Page<PlanSg> findAll(Page<PlanSg> page, String year);
}




