package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolSgMajorDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolQjMajor;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSgMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSgMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolSgMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j
@RequestMapping("/schoolSgMajor")
public class SchoolSgMajorController {
    private SchoolSgMajorServiceImpl schoolSgMajorService;

    @Autowired
    public void setSchoolSgMajorService(SchoolSgMajorServiceImpl schoolSgMajorService){
        this.schoolSgMajorService = schoolSgMajorService;
    }

    @PostMapping("/list")
    public ResponseResult getAllSchoolSgMajorByPage(@RequestBody SchoolSgMajorDTO schoolSgMajorDTO){
        int currentPage = schoolSgMajorDTO.getCurrentPage();
        int pageSize = schoolSgMajorDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        System.out.println("---查询条件" + schoolSgMajorDTO);
        Page pageSchoolQjMajors = schoolSgMajorService.findAllByPage(pg,schoolSgMajorDTO.getSchool_id(),schoolSgMajorDTO.getDiscipline_id(),schoolSgMajorDTO.getSubject_id(),schoolSgMajorDTO.getYear());
        log.info("---根据各类条件，分页查询学校双高计划专业"+pageSchoolQjMajors.getRecords());
        return ResponseResult.SUCCESS().setData(pageSchoolQjMajors);
    }

    @GetMapping("/listAll/{schoolId}/{year}")
    public ResponseResult getAllSchoolSgMajor(@PathVariable("schoolId") long schoolId,@PathVariable("year") String year){
        List<SchoolSgMajorVo> schoolSgMajorVoList = schoolSgMajorService.findAll(schoolId, year);
        log.info("---查询该学校"+year+"年份的双高计划专业" + schoolSgMajorVoList);
        return ResponseResult.SUCCESS().setData(schoolSgMajorVoList);
    }

    @PostMapping("/add")
    public ResponseResult addSchoolSgMajor(@RequestBody SchoolSgMajor addSchoolSgMajor){
        // 过滤重复的学校专业
        QueryWrapper<SchoolSgMajor> qw = new QueryWrapper<>();
        qw.eq("school_sg_major_school",addSchoolSgMajor.getSchool_sg_major_school());
        qw.eq("school_sg_major_major",addSchoolSgMajor.getSchool_sg_major_major());
        qw.eq("school_sg_major_year",addSchoolSgMajor.getSchool_sg_major_year());
        SchoolSgMajor schoolSgMajor = schoolSgMajorService.getOne(qw);
        if (schoolSgMajor==null){
            Boolean result = schoolSgMajorService.save(addSchoolSgMajor);
            if (result){
                log.info("---新增该学校双高计划专业"+addSchoolSgMajor);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---新增失败"+addSchoolSgMajor);
                return ResponseResult.FAILED("新增失败");
            }
        }
        else {
            log.info("---该学校在该年份已存在该双高计划专业");
            return ResponseResult.FAILED("该学校在该年份已存在该双高计划专业,请勿重复添加");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchoolSgMajor(@RequestBody SchoolSgMajor updateSchoolSgMajor){
        // 过滤重复的学校专业
        QueryWrapper<SchoolSgMajor> qw = new QueryWrapper<>();
        qw.eq("school_sg_major_school",updateSchoolSgMajor.getSchool_sg_major_school());
        qw.eq("school_sg_major_major",updateSchoolSgMajor.getSchool_sg_major_major());
        qw.eq("school_sg_major_year",updateSchoolSgMajor.getSchool_sg_major_year());
        SchoolSgMajor schoolSgMajor = schoolSgMajorService.getOne(qw);
        if (schoolSgMajor==null){
            boolean result = schoolSgMajorService.updateById(updateSchoolSgMajor);
            if (result){
                log.info("---修改成功"+updateSchoolSgMajor);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---修改失败"+updateSchoolSgMajor);
                return ResponseResult.FAILED("修改失败");
            }
        }
        else {
            log.info("---该学校在该年份已存在该双高计划专业");
            return ResponseResult.FAILED("该学校在该年份已存在该双高计划专业,修改失败");
        }
    }

    @DeleteMapping("/delete/{schoolSgMajorCode}")
    public ResponseResult deleteSchoolQjMajor(@PathVariable("schoolSgMajorCode") long schoolSgMajorCode) {
        SchoolSgMajor schoolSgMajor = schoolSgMajorService.getById(schoolSgMajorCode);
        if (schoolSgMajor != null) {
            boolean result = schoolSgMajorService.removeById(schoolSgMajorCode);
            if (result) {
                log.info("---删除学校双高计划专业成功" + schoolSgMajor);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败" + schoolSgMajor);
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---该学校不存在该双高计划专业");
            return ResponseResult.FAILED("该学校不存在该双高计划专业");
        }
    }

    @GetMapping("/getYearBySchool/{schoolId}")
    public ResponseResult getYearBySchool(@PathVariable("schoolId") long schoolId) {
        List<String> yearList = schoolSgMajorService.getYearsBySchool(schoolId);
        log.info("---查询某学校强基计划专业年份");
        return ResponseResult.SUCCESS().setData(yearList);
    }
}
