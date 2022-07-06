package com.hust.gaokao_data_analysis_system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.pojo.entity.RankUs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author XDL
* @description 针对表【t_rank_us】的数据库操作Mapper
* @createDate 2022-06-27 11:16:06
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.RankUs
*/
@Repository
public interface RankUsMapper extends BaseMapper<RankUs> {
    RankUs findNewest(@Param("us_school") Integer us_school);
    Page<RankUs> findAll(Page<RankUs> page);
}




