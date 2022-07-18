package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankQs;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankXyh;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author XDL
* @description 针对表【t_rank_xyh】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.RankXyh
*/
@Repository
public interface RankXyhMapper extends BaseMapper<RankXyh> {

    RankXyh findNewest(@Param("xyh_school") Integer xyh_school);
    Page<RankXyh> findAll(Page<RankXyh> page,String year);
}




