package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolMajorDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolClassVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/list")
    public ResponseResult getAllSchoolMajorsByPage(@RequestBody SchoolMajorDTO schoolMajorDTO) {
        int currentPage = schoolMajorDTO.getCurrentPage();
        int pageSize = schoolMajorDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        // 根据schoolId筛选
        System.out.println("---查询条件" + schoolMajorDTO);
        Page<SchoolMajorVo> schoolMajorVoPage = schoolMajorService.findAllByPage(pg, schoolMajorDTO.getSchool_id());
        log.info("---分页查询学校专业" + schoolMajorVoPage.getRecords());
        return ResponseResult.SUCCESS().setData(schoolMajorVoPage);
    }

    @GetMapping("/listAll/{schoolId}")
    public ResponseResult getAllSchoolMajorsBySchool(@PathVariable("schoolId") long schoolId) {
        List<SchoolMajorVo> schoolMajorVoList = schoolMajorService.findAll(schoolId);
        log.info("---查询该学校所有专业" + schoolMajorVoList);
        return ResponseResult.SUCCESS().setData(schoolMajorVoList);
    }

    @PostMapping("/add")
    public ResponseResult addSchoolMajor(@RequestBody SchoolMajor addSchoolMajor) {
        QueryWrapper<SchoolMajor> qw = new QueryWrapper<>();
        qw.eq("school_major_school", addSchoolMajor.getSchool_major_school());
        qw.eq("school_major_major", addSchoolMajor.getSchool_major_major());
        SchoolMajor schoolMajor = schoolMajorService.getOne(qw);
        if (schoolMajor == null) {
            boolean result = schoolMajorService.save(addSchoolMajor);
            if (result) {
                log.info("---新增学校专业" + addSchoolMajor);
                return ResponseResult.SUCCESS().setData(addSchoolMajor);
            } else {
                log.info("---新增学校失败" + addSchoolMajor);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---该学校存在此专业，请勿重复添加" + addSchoolMajor);
            return ResponseResult.FAILED("该学校存在此专业，请勿重复添加");
        }
    }

    @PostMapping("/update")
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

    @DeleteMapping("/delete/{schoolMajorCode}")
    public ResponseResult deleteSchoolMajor(@PathVariable("schoolMajorCode") long schoolMajorCode) {
        SchoolMajor schoolMajor = schoolMajorService.getById(schoolMajorCode);
        if (schoolMajor != null) {
            boolean result = schoolMajorService.removeById(schoolMajor);
            if (result) {
                log.info("---删除成功" + schoolMajor);
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
