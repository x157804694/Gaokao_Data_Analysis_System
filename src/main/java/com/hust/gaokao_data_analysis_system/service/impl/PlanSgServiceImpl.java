package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanSg;
import com.hust.gaokao_data_analysis_system.service.PlanSgService;
import com.hust.gaokao_data_analysis_system.mapper.PlanSgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_plan_sg】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class PlanSgServiceImpl extends ServiceImpl<PlanSgMapper, PlanSg>
    implements PlanSgService{

    private PlanSgMapper sgMapper;

    @Autowired
    public void setSgMapper(PlanSgMapper sgMapper){
        this.sgMapper = sgMapper;
    }

    @Override
    public Page<PlanSg> findAllByPage(Page<PlanSg> page, String year) {
        return sgMapper.findAllByPage(page,year);
    }

    @Override
    public List<PlanSg> findAllByYear(String year) {
        return sgMapper.findAllByYear(year);
    }
}




