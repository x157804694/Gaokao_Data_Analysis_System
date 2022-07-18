package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankWsl;
import com.hust.gaokao_data_analysis_system.service.RankWslService;
import com.hust.gaokao_data_analysis_system.mapper.RankWslMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_rank_wsl】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class RankWslServiceImpl extends ServiceImpl<RankWslMapper, RankWsl>
    implements RankWslService{

    private RankWslMapper wslMapper;

    @Autowired
    public void setWslMapper(RankWslMapper wslMapper) {
        this.wslMapper = wslMapper;
    }

    @Override
    public RankWsl findNewest(Integer wsl_school) {
        return wslMapper.findNewest(wsl_school);
    }

    @Override
    public Page<RankWsl> findAll(Page<RankWsl> page, String year) {
        return wslMapper.findAll(page, year);
    }
}




