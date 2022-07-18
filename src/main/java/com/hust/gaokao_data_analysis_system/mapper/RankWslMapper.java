package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankWsl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author XDL
* @description 针对表【t_rank_wsl】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.RankWsl
*/
@Repository
public interface RankWslMapper extends BaseMapper<RankWsl> {
    RankWsl findNewest(@Param("wsl_school") Integer wsl_school);
    Page<RankWsl> findAll(Page<RankWsl> page, String year);
}




