package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankTws;
import com.hust.gaokao_data_analysis_system.service.RankTwsService;
import com.hust.gaokao_data_analysis_system.mapper.RankTwsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_rank_tws】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class RankTwsServiceImpl extends ServiceImpl<RankTwsMapper, RankTws>
    implements RankTwsService{

    private RankTwsMapper twsMapper;

    @Autowired
    public void setTwsMapper(RankTwsMapper twsMapper) {
        this.twsMapper = twsMapper;
    }

    @Override
    public RankTws findNewest(Integer tws_school) {
        return twsMapper.findNewest(tws_school);
    }

    @Override
    public Page<RankTws> findAll(Page<RankTws> page, String year) {
        return twsMapper.findAll(page,year);
    }
}




