package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanDual;
import com.hust.gaokao_data_analysis_system.service.PlanDualService;
import com.hust.gaokao_data_analysis_system.mapper.PlanDualMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_plan_dual】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class PlanDualServiceImpl extends ServiceImpl<PlanDualMapper, PlanDual>
    implements PlanDualService{

    private PlanDualMapper dualMapper;

    @Autowired
    public void setDualMapper(PlanDualMapper dualMapper){
        this.dualMapper = dualMapper;
    }

    @Override
    public Page<PlanDual> findAll(Page<PlanDual> page) {
        return dualMapper.findAll(page);
    }
}




