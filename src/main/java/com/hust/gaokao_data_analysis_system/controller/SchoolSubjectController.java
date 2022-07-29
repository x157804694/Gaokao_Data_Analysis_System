package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolSubjectDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolSubjectVo;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolSubjectServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolSubject")
@Log4j
public class SchoolSubjectController {
    private SchoolSubjectServiceImpl schoolSubjectService;

    @Autowired
    public void setSchoolSubjectService(SchoolSubjectServiceImpl schoolSubjectService) {
        this.schoolSubjectService = schoolSubjectService;
    }

    @PostMapping("/list")
    public ResponseResult getAllSchoolSubjectByPage(@RequestBody SchoolSubjectDTO schoolSubjectDTO) {
        int currentPage = schoolSubjectDTO.getCurrentPage();
        int pageSize = schoolSubjectDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        System.out.println("---查询条件"+schoolSubjectDTO);
        Page pageSchoolSubjects = schoolSubjectService.findAllByPage(pg, schoolSubjectDTO.getSchool_id(), schoolSubjectDTO.getDiscipline_id());
        log.info("---根据各类条件，分页查询所有学校一级学科" + pageSchoolSubjects.getRecords());
        return ResponseResult.SUCCESS().setData(pageSchoolSubjects);
    }

    @GetMapping("/listAll/{schoolId}")
    public ResponseResult getAllSchoolSubjectBySchoolId(@PathVariable("schoolId") long schoolId) {
        List<SchoolSubjectVo> schoolSubjectVoList = schoolSubjectService.findAll(schoolId, null);
        log.info("---查询该学校所有一级学科" + schoolSubjectVoList);
        return ResponseResult.SUCCESS().setData(schoolSubjectVoList);
    }

    @GetMapping("/listAll/{schoolId}/{disciplineId}")
    public ResponseResult getAllSchoolSubjectBySchoolId(@PathVariable("schoolId") long schoolId,@PathVariable("disciplineId") String disciplineId) {
        List<SchoolSubjectVo> schoolSubjectVoList = schoolSubjectService.findAll(schoolId, disciplineId);
        log.info("---查询该学校某门类下所有一级学科" + schoolSubjectVoList);
        return ResponseResult.SUCCESS().setData(schoolSubjectVoList);
    }

    @PostMapping("/add")
    public ResponseResult addSchoolSubject(@RequestBody SchoolSubject addSchoolSubject) {
        QueryWrapper<SchoolSubject> qw = new QueryWrapper<>();
        qw.eq("school_subject_school", addSchoolSubject.getSchool_subject_school());
        qw.eq("school_subject_subject", addSchoolSubject.getSchool_subject_subject());
        SchoolSubject schoolSubject = schoolSubjectService.getOne(qw);
        if (schoolSubject == null) {
            boolean result = schoolSubjectService.save(addSchoolSubject);
            if (result) {
                log.info("---新增学校一级学科" + addSchoolSubject);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---新增失败" + addSchoolSubject);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---该学校已存在此一级学科");
            return ResponseResult.FAILED("该学校已存在此一级学科，请勿重复添加");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchoolSubject(@RequestBody SchoolSubject updateSchoolSubject) {
        boolean result = schoolSubjectService.updateById(updateSchoolSubject);
        if (result) {
            log.info("---修改成功" + updateSchoolSubject);
            return ResponseResult.SUCCESS();
        } else {
            log.info("---修改失败" + updateSchoolSubject);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{schoolSubjectCode}")
    public ResponseResult deleteSchoolSubject(@PathVariable("schoolSubjectCode") long schoolSubjectCode) {
        SchoolSubject schoolSubject = schoolSubjectService.getById(schoolSubjectCode);
        if (schoolSubject != null) {
            boolean result = schoolSubjectService.removeById(schoolSubjectCode);
            if (result) {
                log.info("---删除成功" + schoolSubject);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败" + schoolSubject);
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---该学校一级学科不存在");
            return ResponseResult.FAILED("该学校一级学科不存在");
        }
    }
}
