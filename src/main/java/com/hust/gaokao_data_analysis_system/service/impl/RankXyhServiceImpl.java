package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankXyh;
import com.hust.gaokao_data_analysis_system.service.RankXyhService;
import com.hust.gaokao_data_analysis_system.mapper.RankXyhMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_rank_xyh】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class RankXyhServiceImpl extends ServiceImpl<RankXyhMapper, RankXyh>
    implements RankXyhService{

    private RankXyhMapper xyhMapper;

    @Autowired
    public void setXyhMapper(RankXyhMapper xyhMapper) {
        this.xyhMapper = xyhMapper;
    }

    @Override
    public RankXyh findNewest(Integer xyh_school) {
        return xyhMapper.findNewest(xyh_school);
    }

    @Override
    public Page<RankXyh> findAll(Page<RankXyh> page) {
        return xyhMapper.findAll(page);
    }
}




