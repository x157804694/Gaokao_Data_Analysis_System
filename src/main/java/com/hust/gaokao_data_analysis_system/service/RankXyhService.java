package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankXyh;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author XDL
* @description 针对表【t_rank_xyh】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface RankXyhService extends IService<RankXyh> {

    RankXyh findNewest(Integer xyh_school);

    Page<RankXyh> findAll(Page<RankXyh> page);
}
