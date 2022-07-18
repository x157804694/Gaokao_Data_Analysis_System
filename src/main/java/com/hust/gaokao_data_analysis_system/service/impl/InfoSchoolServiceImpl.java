package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool;
import com.hust.gaokao_data_analysis_system.service.InfoSchoolService;
import com.hust.gaokao_data_analysis_system.mapper.InfoSchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_info_school】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class InfoSchoolServiceImpl extends ServiceImpl<InfoSchoolMapper, InfoSchool>
    implements InfoSchoolService{
    private InfoSchoolMapper schoolMapper;
    @Autowired
    public void setSchoolMapper(InfoSchoolMapper schoolMapper){
        this.schoolMapper = schoolMapper;
    }
    @Override
    public int addBatch(List<InfoSchool> schoolList) {
        return schoolMapper.addBatch(schoolList);
    }
}




