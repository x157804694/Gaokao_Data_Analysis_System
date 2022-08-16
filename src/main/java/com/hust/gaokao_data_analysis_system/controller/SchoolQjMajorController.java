package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolQjMajorDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolQjMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolQjMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolQjMajorServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolSgMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j
@RequestMapping("/schoolQjMajor")
public class SchoolQjMajorController {
    private SchoolQjMajorServiceImpl schoolQjMajorService;

    @Autowired
    public void setSchoolQjMajorService(SchoolQjMajorServiceImpl schoolQjMajorService) {
        this.schoolQjMajorService = schoolQjMajorService;
    }

    @PostMapping("/list")
    public ResponseResult getAllSchoolQjMajorByPage(@RequestBody SchoolQjMajorDTO schoolQjMajorDTO) {
        int currentPage = schoolQjMajorDTO.getCurrentPage();
        int pageSize = schoolQjMajorDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        System.out.println("---查询条件" + schoolQjMajorDTO);
        Page pageSchoolQjMajors = schoolQjMajorService.findAllByPage(pg, schoolQjMajorDTO.getSchool_id(), schoolQjMajorDTO.getDiscipline_id(), schoolQjMajorDTO.getSubject_id(), schoolQjMajorDTO.getYear());
        log.info("---根据各类条件，分页查询学校强基计划专业" + pageSchoolQjMajors.getRecords());
        return ResponseResult.SUCCESS().setData(pageSchoolQjMajors);
    }

    @GetMapping("/listAll/{schoolId}/{year}")
    public ResponseResult getAllSchoolQjMajor(@PathVariable("schoolId") long schoolId, @PathVariable("year") String year) {
        List<SchoolQjMajorVo> schoolQjMajorVoList = schoolQjMajorService.findAll(schoolId, year);
        log.info("---查询该学校" + year + "年份的强基计划专业" + schoolQjMajorVoList);
        return ResponseResult.SUCCESS().setData(schoolQjMajorVoList);
    }

    @PostMapping("/add")
    public ResponseResult addSchoolQjMajor(@RequestBody SchoolQjMajor addSchoolQjMajor) {
        // 过滤重复的学校专业
        QueryWrapper<SchoolQjMajor> qw = new QueryWrapper<>();
        qw.eq("school_qj_major_school", addSchoolQjMajor.getSchool_qj_major_school());
        qw.eq("school_qj_major_major", addSchoolQjMajor.getSchool_qj_major_major());
        qw.eq("school_qj_major_year", addSchoolQjMajor.getSchool_qj_major_year());
        SchoolQjMajor schoolQjMajor = schoolQjMajorService.getOne(qw);
        if (schoolQjMajor == null) {
            Boolean result = schoolQjMajorService.save(addSchoolQjMajor);
            if (result) {
                log.info("---新增该学校强基计划专业" + addSchoolQjMajor);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---新增失败" + addSchoolQjMajor);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---该学校在该年份已存在该强基计划专业");
            return ResponseResult.FAILED("该学校在该年份已存在该强基计划专业,请勿重复添加");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchoolQjMajor(@RequestBody SchoolQjMajor updateSchoolQjMajor) {
        // 过滤重复的学校专业
        QueryWrapper<SchoolQjMajor> qw = new QueryWrapper<>();
        qw.eq("school_qj_major_school", updateSchoolQjMajor.getSchool_qj_major_school());
        qw.eq("school_qj_major_major", updateSchoolQjMajor.getSchool_qj_major_major());
        qw.eq("school_qj_major_year", updateSchoolQjMajor.getSchool_qj_major_year());
        SchoolQjMajor schoolQjMajor = schoolQjMajorService.getOne(qw);
        if (schoolQjMajor == null) {
            boolean result = schoolQjMajorService.updateById(updateSchoolQjMajor);
            if (result) {
                log.info("---修改成功" + updateSchoolQjMajor);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---修改失败" + updateSchoolQjMajor);
                return ResponseResult.FAILED("修改失败");
            }
        } else {
            log.info("---该学校在该年份已存在该强基计划专业");
            return ResponseResult.FAILED("该学校在该年份已存在该强基计划专业,修改失败");
        }
    }

    @DeleteMapping("/delete/{schoolQjMajorCode}")
    public ResponseResult deleteSchoolQjMajor(@PathVariable("schoolQjMajorCode") long schoolQjMajorCode) {
        SchoolQjMajor schoolQjMajor = schoolQjMajorService.getById(schoolQjMajorCode);
        if (schoolQjMajor != null) {
            boolean result = schoolQjMajorService.removeById(schoolQjMajorCode);
            if (result) {
                log.info("---删除学校强基计划专业成功" + schoolQjMajor);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败" + schoolQjMajor);
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---该学校不存在该强基计划专业");
            return ResponseResult.FAILED("该学校不存在该强基计划专业");
        }
    }

    @GetMapping("/getYearBySchool/{schoolId}")
    public ResponseResult getYearBySchool(@PathVariable("schoolId") long schoolId) {
        List<String> yearList = schoolQjMajorService.getYearsBySchool(schoolId);
        log.info("---查询某学校强基计划专业年份");
        return ResponseResult.SUCCESS().setData(yearList);
    }
}
