package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankRk;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author XDL
* @description 针对表【t_rank_rk】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface RankRkService extends IService<RankRk> {

    RankRk findNewest(Integer rk_school);

    Page<RankRk> findAll(Page<RankRk> page);
}
