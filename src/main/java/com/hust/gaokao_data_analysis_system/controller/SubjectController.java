/**
 * @Description:
 * @Package: com.hust.gaokao_data_analysis_system.controller
 * @ClassName: SubjectController
 * @Author: 徐鼎立
 * @Date: 2022/7/5 22:26
 * @version: 1.0
 * Copyright (c) 2022,All Rights Reserved.
 */
package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SubjectInfoDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSubject;
import com.hust.gaokao_data_analysis_system.service.impl.InfoSubjectServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@Log4j
public class SubjectController {
    private InfoSubjectServiceImpl subjectService;

    @Autowired
    public void setSubjectService(InfoSubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/list")
    public ResponseResult getAllSubjectByPage(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        int currentPage = subjectInfoDTO.getCurrentPage();
        int pageSize = subjectInfoDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String disciplineId = subjectInfoDTO.getDiscipline_id();
        String disciplineLevel = subjectInfoDTO.getDiscipline_level();
        Page pageSubjects = subjectService.findAllByPage(pg,disciplineId,disciplineLevel);
        log.info("---分页查询一级学科" + pageSubjects.getRecords());
        return ResponseResult.SUCCESS().setData(pageSubjects);
    }

    @GetMapping("/listAll/{disciplineId}")
    public ResponseResult getAllSubjectByDiscipline(@PathVariable("disciplineId") String disciplineId) {
        QueryWrapper<InfoSubject> qw = new QueryWrapper<>();
        qw.eq("subject_discipline",disciplineId);
        qw.orderByAsc("subject_id");
        List<InfoSubject> subjects = subjectService.list(qw);
        log.info("---根据学科门类查询所有一级学科" + subjects);
        return ResponseResult.SUCCESS().setData(subjects);
    }

    @PostMapping("/add")
    public ResponseResult addSubject(@RequestBody InfoSubject addSubject) {
        QueryWrapper<InfoSubject> qw = new QueryWrapper<>();
        InfoSubject subject = subjectService.getOne(qw.eq("subject_id", addSubject.getSubject_id()));
        if (subject == null) {
            boolean result = subjectService.save(addSubject);
            if (result) {
                log.info("---新增一级学科" + addSubject);
                return ResponseResult.SUCCESS().setData(addSubject);
            } else {
                log.info("---新增失败" + addSubject);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---存在同名一级学科，请勿重复添加");
            return ResponseResult.FAILED("一级学科id已存在，请勿重复添加！");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSubject(@RequestBody InfoSubject updateSubject) {
        boolean result = subjectService.updateById(updateSubject);
        if (result) {
            log.info("---修改一级学科成功" + updateSubject);
            return ResponseResult.SUCCESS();
        }
        else {
            log.info("---修改一级学科失败" + updateSubject);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{subjectCode}")
    public ResponseResult deleteSubject(@PathVariable("subjectCode") long subjectCode){
        InfoSubject subject = subjectService.getById(subjectCode);
        if (subject!=null){
            boolean result = subjectService.removeById(subjectCode);
            if (result){
                log.info("---删除学科成功"+subjectCode);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---删除学科失败"+subjectCode);
                return ResponseResult.FAILED("删除失败");
            }
        }
        else {
            log.info("---一级学科编号不存在");
            return ResponseResult.FAILED("一级学科编号不存在");
        }
    }
}
