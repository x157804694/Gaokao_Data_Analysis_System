package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankRk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author XDL
* @description 针对表【t_rank_rk】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.RankRk
*/
@Repository
public interface RankRkMapper extends BaseMapper<RankRk> {

    RankRk findNewest(@Param("rk_school") Integer rk_school);
    Page<RankRk> findAll(Page<RankRk> page,String year);
}




