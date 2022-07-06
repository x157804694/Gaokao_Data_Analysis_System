package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankWsl;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author XDL
* @description 针对表【t_rank_wsl】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface RankWslService extends IService<RankWsl> {

    RankWsl findNewest(Integer wsl_school);

    Page<RankWsl> findAll(Page<RankWsl> page);
}
