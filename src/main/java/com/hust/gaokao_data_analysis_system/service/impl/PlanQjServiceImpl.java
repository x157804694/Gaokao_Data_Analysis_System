package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanQj;
import com.hust.gaokao_data_analysis_system.service.PlanQjService;
import com.hust.gaokao_data_analysis_system.mapper.PlanQjMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_plan_qj】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class PlanQjServiceImpl extends ServiceImpl<PlanQjMapper, PlanQj>
    implements PlanQjService{

    private PlanQjMapper qjMapper;

    @Autowired
    public void setQjMapper(PlanQjMapper qjMapper){
        this.qjMapper = qjMapper;
    }

    @Override
    public Page<PlanQj> findAll(Page<PlanQj> page, String year) {
        return qjMapper.findAll(page,year);
    }
}




