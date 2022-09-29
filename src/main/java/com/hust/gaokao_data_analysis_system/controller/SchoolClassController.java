package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolClassDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolClass;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolClassSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolClassVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolClassServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolClassSubjectServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schoolClass")
@Log4j
public class SchoolClassController {
    private SchoolClassServiceImpl schoolClassService;
    private SchoolClassSubjectServiceImpl schoolClassSubjectService;

    @Autowired
    public void setSchoolClassService(SchoolClassServiceImpl schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @Autowired
    public void setSchoolClassSubjectService(SchoolClassSubjectServiceImpl schoolClassSubjectService) {
        this.schoolClassSubjectService = schoolClassSubjectService;
    }

    @PostMapping("/list")
    public ResponseResult getAllSchoolClassesByPage(@RequestBody SchoolClassDTO schoolClassDTO) {
        int currentPage = schoolClassDTO.getCurrentPage();
        int pageSize = schoolClassDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        // 根据schoolId筛选
        System.out.println("---查询条件" + schoolClassDTO);
        Page<SchoolClassVo> SchoolClassVoPage = schoolClassService.findAllByPage(pg, schoolClassDTO.getSchool_id());
        log.info("---分页查询学校班级" + SchoolClassVoPage.getRecords());
        return ResponseResult.SUCCESS().setData(SchoolClassVoPage);
    }

    @GetMapping("/listAll/{schoolId}")
    public ResponseResult getAllSchoolClassesBySchool(@PathVariable("schoolId") long schoolId) {
        List<SchoolClassVo> SchoolClassVoList = schoolClassService.findAll(schoolId);
        log.info("---查询该学校所有班级" + SchoolClassVoList);
        return ResponseResult.SUCCESS().setData(SchoolClassVoList);
    }

    @PostMapping("/add")
    public ResponseResult addSchoolClass(@RequestBody SchoolClass addSchoolClass) {
        QueryWrapper<SchoolClass> qw = new QueryWrapper<>();
        qw.eq("school_class_school", addSchoolClass.getSchool_class_school());
        qw.eq("school_class_name", addSchoolClass.getSchool_class_name());
        SchoolClass schoolClass = schoolClassService.getOne(qw);
        if (schoolClass == null) {
            boolean result = schoolClassService.save(addSchoolClass);
            if (result) {
                log.info("---新增学校班级" + addSchoolClass);
                // 新增成功后将对应的专业列表添加到中间表中
                List<String> infoSubjectIdList = addSchoolClass.getSubjectIdList();
                List<SchoolClassSubject> schoolClassSubjectList = new ArrayList<>();
                for (String subjectId : infoSubjectIdList) {
                    SchoolClassSubject schoolClassSubject = new SchoolClassSubject();
                    schoolClassSubject.setSchoolClass_subject_class(addSchoolClass.getSchool_class_code());
                    schoolClassSubject.setSchoolClass_subject_subject(subjectId);
                    schoolClassSubjectList.add(schoolClassSubject);
                }
                boolean result2 = schoolClassSubjectService.saveBatch(schoolClassSubjectList);
                if (result2) {
                    log.info("---新增学校班级-学科中间表记录" + schoolClassSubjectList);
                } else {
                    log.info("---新增学校班级-学科中间表记录失败" + schoolClassSubjectList);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---新增失败" + addSchoolClass);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---该学校已存在此班级");
            return ResponseResult.FAILED("该学校已存在此班级，请勿重复添加");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchoolClass(@RequestBody SchoolClass updateSchoolClass) {
        boolean result = schoolClassService.updateById(updateSchoolClass);
        // 第一步，先修改学校班级表
        if (result) {
            log.info("---修改成功" + updateSchoolClass);
        } else {
            log.info("---修改失败" + updateSchoolClass);
            return ResponseResult.FAILED("修改失败");
        }
        // 第二步，删除学校班级-学科中间表的相关记录
        QueryWrapper<SchoolClassSubject> qw = new QueryWrapper<>();
        qw.eq("schoolClass_subject_class", updateSchoolClass.getSchool_class_code());
        boolean result2 = schoolClassSubjectService.remove(qw);
        if (result2) {
            log.info("---删除学校班级-学科中间表的相关记录成功");
        } else {
            log.info("---删除学校班级-学科中间表的相关记录失败");
            return ResponseResult.FAILED("修改失败");
        }
        // 第三步，添加修改后的记录
        List<String> infoSubjectList = updateSchoolClass.getSubjectIdList();
        List<SchoolClassSubject> schoolClassSubjectList = new ArrayList<>();
        for (String subjectId : infoSubjectList) {
            SchoolClassSubject schoolClassSubject = new SchoolClassSubject();
            schoolClassSubject.setSchoolClass_subject_class(updateSchoolClass.getSchool_class_code());
            schoolClassSubject.setSchoolClass_subject_subject(subjectId);
            schoolClassSubjectList.add(schoolClassSubject);
        }
        boolean result3 = schoolClassSubjectService.saveBatch(schoolClassSubjectList);
        if (result3) {
            log.info("---新增学校班级-学科中间表记录" + schoolClassSubjectList);
        } else {
            log.info("---新增学校班级-学科中间表记录失败" + schoolClassSubjectList);
            return ResponseResult.FAILED("修改失败");
        }
        return ResponseResult.SUCCESS();
    }

    @DeleteMapping("/delete/{schoolClassCode}")
    public ResponseResult deleteSchoolSubject(@PathVariable("schoolClassCode") long schoolClassCode) {
        SchoolClass schoolClass = schoolClassService.getById(schoolClassCode);
        if (schoolClass != null) {
            boolean result = schoolClassService.removeById(schoolClassCode);
            if (result) {
                log.info("---删除学校班级" + schoolClass);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败" + schoolClass);
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---该学校班级不存在");
            return ResponseResult.FAILED("该学校班级不存在");
        }
    }
}
