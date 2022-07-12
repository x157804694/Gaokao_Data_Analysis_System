package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.LqSchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.LqSchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.LqSchoolMajorService;
import com.hust.gaokao_data_analysis_system.mapper.LqSchoolMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_lq_school_major】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class LqSchoolMajorServiceImpl extends ServiceImpl<LqSchoolMajorMapper, LqSchoolMajor>
    implements LqSchoolMajorService{
    private LqSchoolMajorMapper lqSchoolMajorMapper;
    @Autowired
    public void setLqSchoolMajorMapper(LqSchoolMajorMapper lqSchoolMajorMapper){
        this.lqSchoolMajorMapper = lqSchoolMajorMapper;
    }
    @Override
    public Page<LqSchoolMajorVo> findAllByPage(Page<LqSchoolMajorVo> page) {
        return lqSchoolMajorMapper.findAllByPage(page);
    }

    @Override
    public List<LqSchoolMajorVo> findAll(long schoolId) {
        return lqSchoolMajorMapper.findAll(schoolId);
    }
}




