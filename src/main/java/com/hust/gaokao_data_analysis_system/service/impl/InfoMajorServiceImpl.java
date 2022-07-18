package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.mapper.InfoSubjectMapper;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.MajorVo;
import com.hust.gaokao_data_analysis_system.service.InfoMajorService;
import com.hust.gaokao_data_analysis_system.mapper.InfoMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_info_major】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class InfoMajorServiceImpl extends ServiceImpl<InfoMajorMapper, InfoMajor>
    implements InfoMajorService{
    private InfoMajorMapper majorMapper;
    @Autowired
    public void setMajorMapper(InfoMajorMapper majorMapper){
        this.majorMapper = majorMapper;
    }

    @Override
    public Page<MajorVo> findAllByPage(Page<MajorVo> page, String disciplineLevel, String disciplineId, String subjectId) {
        return majorMapper.findAllByPage(page, disciplineLevel, disciplineId, subjectId);
    }
}




