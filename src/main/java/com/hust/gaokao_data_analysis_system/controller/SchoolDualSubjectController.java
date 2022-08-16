package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolDualSubjectDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDualSubject;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDualSubjectVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolDualSubjectServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolSubjectServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolDualSubject")
@Log4j
public class SchoolDualSubjectController {
    private SchoolDualSubjectServiceImpl schoolDualSubjectService;
    private SchoolSubjectServiceImpl schoolSubjectService;

    @Autowired
    public void setSchoolDualSubjectService(SchoolDualSubjectServiceImpl schoolDualSubjectService) {
        this.schoolDualSubjectService = schoolDualSubjectService;
    }

    @Autowired
    public void setSchoolSubjectService(SchoolSubjectServiceImpl schoolSubjectService) {
        this.schoolSubjectService = schoolSubjectService;
    }

    @PostMapping("/list")
    public ResponseResult getAllByCols(@RequestBody SchoolDualSubjectDTO schoolDualSubjectDTO) {
        int currentPage = schoolDualSubjectDTO.getCurrentPage();
        int pageSize = schoolDualSubjectDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        System.out.println("---查询条件" + schoolDualSubjectDTO);
        Page pageSchoolSubjects = schoolDualSubjectService.findAllByPage(pg, schoolDualSubjectDTO.getSchool_id(), schoolDualSubjectDTO.getDiscipline_id(), schoolDualSubjectDTO.getYear());
        log.info("---根据各类条件，分页查询学校双一流学科" + pageSchoolSubjects.getRecords());
        return ResponseResult.SUCCESS().setData(pageSchoolSubjects);
    }

    @GetMapping("/listAll/{schoolId}/{year}")
    public ResponseResult getAllSchoolDualSubjectBySchool(@PathVariable("schoolId") long schoolId,@PathVariable("year") String year) {
        List<SchoolDualSubjectVo> schoolDualSubjectVoList = schoolDualSubjectService.findAll(schoolId, year);
        log.info("---查询该学校"+year+"年份的双一流学科" + schoolDualSubjectVoList);
        return ResponseResult.SUCCESS().setData(schoolDualSubjectVoList);
    }

    @PostMapping("/add")
    public ResponseResult addSchoolDualSubject(@RequestBody SchoolDualSubject addSchoolDualSubject) {
        // 过滤重复学科
        QueryWrapper<SchoolDualSubject> qw = new QueryWrapper<>();
        qw.eq("school_dual_subject_school_subject", addSchoolDualSubject.getSchool_dual_subject_school_subject());
        qw.eq("school_dual_subject_year", addSchoolDualSubject.getSchool_dual_subject_year());
        SchoolDualSubject schoolDualSubject = schoolDualSubjectService.getOne(qw);
        if (schoolDualSubject == null) {
            boolean result = schoolDualSubjectService.save(addSchoolDualSubject);
            if (result) {
                log.info("---新增该学校双一流学科" + addSchoolDualSubject);
                String year = addSchoolDualSubject.getSchool_dual_subject_year();
                // 如果新增的最新年份是2022
                if(year.equals("2022")) {
                    // 将学校一级学科字段中的是否双一流标志修改为1
                    SchoolSubject schoolSubject = schoolSubjectService.getById(addSchoolDualSubject.getSchool_dual_subject_school_subject());
                    schoolSubject.setSchool_subject_dual_flag(1);
                    schoolSubjectService.updateById(schoolSubject);
                    System.out.println("---修改双一流标记成功");
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---新增失败" + addSchoolDualSubject);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---该学校在该年份已存在该双一流学科");
            return ResponseResult.FAILED("该学校在该年份已存在该双一流学科,请勿重复添加");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchoolDualSubject(@RequestBody SchoolDualSubject updateSchoolDualSubject) {
        // 过滤重复学科
        QueryWrapper<SchoolDualSubject> qw = new QueryWrapper<>();
        qw.eq("school_dual_subject_school_subject", updateSchoolDualSubject.getSchool_dual_subject_school_subject());
        qw.eq("school_dual_subject_year", updateSchoolDualSubject.getSchool_dual_subject_year());
        SchoolDualSubject schoolDualSubject = schoolDualSubjectService.getOne(qw);
        if (schoolDualSubject==null){
            boolean result = schoolDualSubjectService.updateById(updateSchoolDualSubject);
            if (result) {
                log.info("---修改成功" + updateSchoolDualSubject);
                String year = updateSchoolDualSubject.getSchool_dual_subject_year();
                // 如果修改的最新年份是2022
                if(year.equals("2022")) {
                    // 将学校一级学科字段中的是否双一流标志修改为1
                    SchoolSubject schoolSubject = schoolSubjectService.getById(updateSchoolDualSubject.getSchool_dual_subject_school_subject());
                    schoolSubject.setSchool_subject_dual_flag(1);
                    schoolSubjectService.updateById(schoolSubject);
                    System.out.println("---修改双一流标记成功1");
                }
                // 如果修改的最新年份不是2022年，并且不存在2022年双一流学科的记录，那么将是否双一流标志修改为0
                else {
                    QueryWrapper<SchoolDualSubject> qw2 = new QueryWrapper<>();
                    qw.eq("school_dual_subject_school_subject", updateSchoolDualSubject.getSchool_dual_subject_school_subject());
                    qw.eq("school_dual_subject_year", "2022");
                    SchoolDualSubject schoolDualSubject2022 = schoolDualSubjectService.getOne(qw);
                    if (schoolDualSubject2022==null){
                        // 将学校一级学科字段中的是否双一流标志修改为0
                        SchoolSubject schoolSubject = schoolSubjectService.getById(updateSchoolDualSubject.getSchool_dual_subject_school_subject());
                        schoolSubject.setSchool_subject_dual_flag(0);
                        schoolSubjectService.updateById(schoolSubject);
                        System.out.println("---修改双一流标记成功0");
                    }
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---修改失败" + updateSchoolDualSubject);
                return ResponseResult.FAILED("修改失败");
            }
        }
        else {
            log.info("---该学校在该年份已存在该双一流学科");
            return ResponseResult.FAILED("该学校在该年份已存在该双一流学科,修改失败");
        }
    }

    @DeleteMapping("/delete/{schoolDualSubjectCode}")
    public ResponseResult deleteSchoolDualSubject(@PathVariable("schoolDualSubjectCode") long schoolDualSubjectCode) {
        SchoolDualSubject schoolDualSubject = schoolDualSubjectService.getById(schoolDualSubjectCode);
        if (schoolDualSubject != null) {
            boolean result = schoolDualSubjectService.removeById(schoolDualSubjectCode);
            if (result) {
                log.info("---删除成功" + schoolDualSubject);
                // 如果删除最新(2022)年份的双一流学科
                String year = schoolDualSubject.getSchool_dual_subject_year();
                if (year.equals("2022")) {
                    // 将学校一级学科字段中的是否双一流标志修改为0
                    SchoolSubject schoolSubject = schoolSubjectService.getById(schoolDualSubject.getSchool_dual_subject_school_subject());
                    schoolSubject.setSchool_subject_dual_flag(0);
                    schoolSubjectService.updateById(schoolSubject);
                    System.out.println("---修改双一流标记成功");
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败" + schoolDualSubject);
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---该学校不存在该双一流级学科");
            return ResponseResult.FAILED("该学校不存在该双一流级学科");
        }
    }

    @GetMapping("/getYearBySchool/{schoolId}")
    public ResponseResult getYearBySchool(@PathVariable("schoolId") long schoolId) {
        List<String> yearList = schoolDualSubjectService.getYearsBySchool(schoolId);
        log.info("---查询某学校双一流学科年份");
        return ResponseResult.SUCCESS().setData(yearList);
    }
}
