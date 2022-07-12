package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.ZsSchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.ZsSchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.ZsSchoolMajorService;
import com.hust.gaokao_data_analysis_system.mapper.ZsSchoolMajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_zs_school_major】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class ZsSchoolMajorServiceImpl extends ServiceImpl<ZsSchoolMajorMapper, ZsSchoolMajor>
    implements ZsSchoolMajorService{
    private ZsSchoolMajorMapper zsSchoolMajorMapper;
    @Autowired
    public void setZsSchoolMajorMapper(ZsSchoolMajorMapper zsSchoolMajorMapper){
        this.zsSchoolMajorMapper = zsSchoolMajorMapper;
    }
    @Override
    public Page<ZsSchoolMajorVo> findAllByPage(Page<ZsSchoolMajorVo> page) {
        return zsSchoolMajorMapper.findAllByPage(page);
    }

    @Override
    public List<ZsSchoolMajorVo> findAll(long schoolId) {
        return zsSchoolMajorMapper.findAll(schoolId);
    }
}




