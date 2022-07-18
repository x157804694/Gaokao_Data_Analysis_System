package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoProvinceControl;
import com.hust.gaokao_data_analysis_system.service.InfoProvinceControlService;
import com.hust.gaokao_data_analysis_system.mapper.InfoProvinceControlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_info_province_control】的数据库操作Service实现
* @createDate 2022-07-11 18:49:15
*/
@Service
public class InfoProvinceControlServiceImpl extends ServiceImpl<InfoProvinceControlMapper, InfoProvinceControl>
    implements InfoProvinceControlService{
    private InfoProvinceControlMapper provinceControlMapper;
    @Autowired
    public void setProvinceControlMapper(InfoProvinceControlMapper provinceControlMapper){
        this.provinceControlMapper = provinceControlMapper;
    }

    @Override
    public List<String> getYearByProvince(String provinceName) {
        return provinceControlMapper.getYearByProvince(provinceName);
    }
}




