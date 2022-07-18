package com.hust.gaokao_data_analysis_system.mapper;

import com.hust.gaokao_data_analysis_system.pojo.entity.InfoProvinceControl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_info_province_control】的数据库操作Mapper
* @createDate 2022-07-11 18:49:15
* @Entity com.hust.gaokao_data_analysis_system.pojo.entity.InfoProvinceControl
*/
@Repository
public interface InfoProvinceControlMapper extends BaseMapper<InfoProvinceControl> {
    List<String> getYearByProvince(String provinceName);
}




