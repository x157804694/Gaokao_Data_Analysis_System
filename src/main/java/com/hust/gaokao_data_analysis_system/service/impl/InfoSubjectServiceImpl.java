package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.SubjectVo;
import com.hust.gaokao_data_analysis_system.service.InfoSubjectService;
import com.hust.gaokao_data_analysis_system.mapper.InfoSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author XDL
* @description 针对表【t_info_subject】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class InfoSubjectServiceImpl extends ServiceImpl<InfoSubjectMapper, InfoSubject>
    implements InfoSubjectService{
    private InfoSubjectMapper subjectMapper;
    @Autowired
    public void setSubjectMapper(InfoSubjectMapper subjectMapper){
        this.subjectMapper = subjectMapper;
    }
    @Override
    public Page<SubjectVo> findAllByPage(Page<SubjectVo> page, String disciplineId, String disciplineLevel) {
        return subjectMapper.findAllByPage(page,disciplineId,disciplineLevel);
    }
}




