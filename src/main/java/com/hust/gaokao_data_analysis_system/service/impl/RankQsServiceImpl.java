package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.hust.gaokao_data_analysis_system.service.RankQsService;
import com.hust.gaokao_data_analysis_system.mapper.RankQsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_rank_qs】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class RankQsServiceImpl extends ServiceImpl<RankQsMapper, RankQs>
    implements RankQsService{

    private RankQsMapper qsMapper;

    @Autowired
    public void setQsMapper(RankQsMapper qsMapper) {
        this.qsMapper = qsMapper;
    }

    @Override
    public RankQs findNewest(Integer qs_school) {    //找到一指定学校在该表排名下的最新年份
        return qsMapper.findNewest(qs_school);
    }

    @Override
    public Page<RankQs> findAll(Page<RankQs> page) {
        return qsMapper.findAll(page);
    }
}




