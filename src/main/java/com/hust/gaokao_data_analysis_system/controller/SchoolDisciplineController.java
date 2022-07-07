package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolDiscipline;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolDisciplineVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolDisciplineServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/schoolDiscipline")
@Log4j
public class SchoolDisciplineController {
    private SchoolDisciplineServiceImpl schoolDisciplineService;
    @Autowired
    public void setSchoolDisciplineService(SchoolDisciplineServiceImpl schoolDisciplineService){
        this.schoolDisciplineService = schoolDisciplineService;
    }

    @RequestMapping("/list")
    public ResponseResult getAllSchoolDisciplineByPage(@RequestBody PageRequest pageRequest){
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page pgSchoolDisciplineVos = schoolDisciplineService.findAllByPage(pg);
        log.info("---分页查询学校学科门类"+pgSchoolDisciplineVos.getRecords());
        return ResponseResult.SUCCESS().setData(pgSchoolDisciplineVos);
    }

    @RequestMapping("/listAll/{schoolId}")
    public ResponseResult getAllSchoolDisciplineBySchoolId(@PathVariable("schoolId") long schoolId){
        List<SchoolDisciplineVo> schoolDisciplineList = schoolDisciplineService.findAll(schoolId);
        log.info("---查询该学校所有学科门类"+schoolDisciplineList);
        return ResponseResult.SUCCESS().setData(schoolDisciplineList);
    }

    @RequestMapping("/add")
    public ResponseResult addSchoolDiscipline(@RequestBody SchoolDiscipline addSchoolDiscipline){
        QueryWrapper<SchoolDiscipline> qw = new QueryWrapper<>();
        qw.eq("school_discipline_school",addSchoolDiscipline.getSchool_discipline_school());
        qw.eq("school_discipline_discipline",addSchoolDiscipline.getSchool_discipline_discipline());
        // 判断是否已存在这条记录
        SchoolDiscipline schoolDiscipline = schoolDisciplineService.getOne(qw);
        if (schoolDiscipline==null){
            boolean result = schoolDisciplineService.save(addSchoolDiscipline);
            if (result){
                log.info("---新增学校学科门类"+addSchoolDiscipline);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---新增失败"+addSchoolDiscipline);
                return ResponseResult.FAILED("新增失败");
            }
        }
        else {
            log.info("---该学校存在此学科门类"+addSchoolDiscipline);
            return ResponseResult.FAILED("该学校存在此学科门类,请勿重复添加");
        }
    }

    @RequestMapping("/update")
    public ResponseResult updateSchoolDiscipline(@RequestBody SchoolDiscipline updateSchoolDiscipline){
        QueryWrapper<SchoolDiscipline> qw = new QueryWrapper<>();
        qw.eq("school_discipline_school",updateSchoolDiscipline.getSchool_discipline_school());
        qw.eq("school_discipline_discipline",updateSchoolDiscipline.getSchool_discipline_discipline());
        // 判断是否已存在这条记录
        SchoolDiscipline schoolDiscipline = schoolDisciplineService.getOne(qw);
        if (schoolDiscipline==null){
            boolean result = schoolDisciplineService.updateById(updateSchoolDiscipline);
            if (result){
                log.info("---修改成功"+updateSchoolDiscipline);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---修改失败"+updateSchoolDiscipline);
                return ResponseResult.FAILED("修改失败");
            }
        }
       else {
            log.info("---该学校存在此学科门类"+updateSchoolDiscipline);
            return ResponseResult.FAILED("该学校存在此学科门类,修改失败");
        }
    }

    @DeleteMapping("/delete/{schoolDisciplineCode}")
    public ResponseResult deleteSchoolDiscipline(@PathVariable("schoolDisciplineCode") long schoolDisciplineCode){
        SchoolDiscipline schoolDiscipline = schoolDisciplineService.getById(schoolDisciplineCode);
        if (schoolDiscipline!=null){
            boolean result = schoolDisciplineService.removeById(schoolDisciplineCode);
            if (result){
                log.info("---删除成功"+schoolDiscipline);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---删除失败"+schoolDiscipline);
                return ResponseResult.FAILED("删除失败");
            }
        }
        else {
            log.info("---该学校学科门类不存在");
            return ResponseResult.FAILED("该学校学科门类不存在");
        }
    }
}
