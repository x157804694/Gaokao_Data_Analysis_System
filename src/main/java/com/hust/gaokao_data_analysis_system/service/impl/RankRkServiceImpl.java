package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankRk;
import com.hust.gaokao_data_analysis_system.service.RankRkService;
import com.hust.gaokao_data_analysis_system.mapper.RankRkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_rank_rk】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class RankRkServiceImpl extends ServiceImpl<RankRkMapper, RankRk>
    implements RankRkService{

    private RankRkMapper rkMapper;

    @Autowired
    public void setRkMapper(RankRkMapper rkMapper) {
        this.rkMapper = rkMapper;
    }

    @Override
    public RankRk findNewest(Integer rk_school) {
        return rkMapper.findNewest(rk_school);
    }

    @Override
    public Page<RankRk> findAll(Page<RankRk> page, String year) {
        return rkMapper.findAll(page,year);
    }
}




