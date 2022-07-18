package com.hust.gaokao_data_analysis_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankUs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author XDL
* @description 针对表【t_rank_us】的数据库操作Service
* @createDate 2022-06-27 11:16:06
*/
public interface RankUsService extends IService<RankUs> {

    RankUs findNewest(Integer us_school);

    Page<RankUs> findAll(Page<RankUs> page, String year);
}
