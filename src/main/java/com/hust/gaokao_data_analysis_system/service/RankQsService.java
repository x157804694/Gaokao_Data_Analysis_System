package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author XDL
* @description 针对表【t_rank_qs】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface RankQsService extends IService<RankQs> {

    RankQs findNewest(Integer qs_school);

    Page<RankQs> findAll(Page<RankQs> page, String year);
}
