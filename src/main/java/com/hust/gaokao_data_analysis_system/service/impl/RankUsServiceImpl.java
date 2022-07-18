package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankUs;
import com.hust.gaokao_data_analysis_system.service.RankUsService;
import com.hust.gaokao_data_analysis_system.mapper.RankUsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_rank_us】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class RankUsServiceImpl extends ServiceImpl<RankUsMapper, RankUs>
    implements RankUsService{

    private RankUsMapper usMapper;

    @Autowired
    public void setUsMapper(RankUsMapper usMapper) {
        this.usMapper = usMapper;
    }

    @Override
    public RankUs findNewest(Integer us_school) {
        return usMapper.findNewest(us_school);
    }

    @Override
    public Page<RankUs> findAll(Page<RankUs> page, String year) {
        return usMapper.findAll(page, year);
    }
}




