package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schoolMajor")
@Log4j
public class SchoolMajorController {
    private SchoolMajorServiceImpl schoolMajorService;

    @Autowired
    public void setSchoolMajorService(SchoolMajorServiceImpl schoolMajorService) {
        this.schoolMajorService = schoolMajorService;
    }

    @RequestMapping("/list")
    public ResponseResult getAllSchoolMajorByPage(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page<SchoolMajorVo> schoolMajorVoPage = schoolMajorService.findAllByPage(pg);
        log.info("---分页查询所有学校专业" + schoolMajorVoPage.getRecords());
        return ResponseResult.SUCCESS().setData(schoolMajorVoPage);
    }

    @RequestMapping("/listAll/{schoolId}")
    public ResponseResult getAllSchoolMajorBySchool(@PathVariable("schoolId") long schoolId) {
        List<SchoolMajorVo> schoolMajorVoList = schoolMajorService.findAll(schoolId);
        log.info("---查询该学校所有专业" + schoolMajorVoList);
        return ResponseResult.SUCCESS().setData(schoolMajorVoList);
    }

    @RequestMapping("/add")
    public ResponseResult addSchoolMajor(@RequestBody SchoolMajor addSchoolMajor) {
        QueryWrapper<SchoolMajor> qw = new QueryWrapper<>();
        qw.eq("school_major_school", addSchoolMajor.getSchool_major_school());
        qw.eq("school_major_name", addSchoolMajor.getSchool_major_name());
        SchoolMajor schoolMajor = schoolMajorService.getOne(qw);
        if (schoolMajor == null) {
            boolean result = schoolMajorService.save(addSchoolMajor);
            if (result) {
                log.info("---新增学校专业" + addSchoolMajor);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---新增失败" + addSchoolMajor);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---该学校已存在此专业");
            return ResponseResult.FAILED("该学校已存在此专业，请勿重复添加");
        }
    }

    @RequestMapping("/update")
    public ResponseResult updateSchoolMajor(@RequestBody SchoolMajor updateSchoolMajor) {
        boolean result = schoolMajorService.updateById(updateSchoolMajor);
        if (result) {
            log.info("---修改成功" + updateSchoolMajor);
            return ResponseResult.SUCCESS();
        } else {
            log.info("---修改失败" + updateSchoolMajor);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @RequestMapping("/delete/{schoolMajorCode}")
    public ResponseResult deleteSchoolMajor(@PathVariable("schoolMajorCode") long schoolMajorCode) {
        SchoolMajor schoolMajor = schoolMajorService.getById(schoolMajorCode);
        if (schoolMajor != null) {
            boolean result = schoolMajorService.removeById(schoolMajorCode);
            if (result) {
                log.info("---删除学校专业" + schoolMajor);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败" + schoolMajor);
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---该学校专业不存在");
            return ResponseResult.FAILED("该学校专业不存在");
        }
    }
}
