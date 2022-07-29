package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.MajorDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoMajor;
import com.hust.gaokao_data_analysis_system.service.impl.InfoMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/major")
@Log4j
public class MajorController {
    private InfoMajorServiceImpl majorService;

    @Autowired
    public void setMajorService(InfoMajorServiceImpl majorService) {
        this.majorService = majorService;
    }

    @PostMapping("/list")
    public ResponseResult getAllMajorByPage(@RequestBody MajorDTO majorDTO) {
        int currentPage = majorDTO.getCurrentPage();
        int pageSize = majorDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String discipline_level = majorDTO.getDiscipline_level();
        String discipline_id = majorDTO.getDiscipline_id();
        String subject_id = majorDTO.getSubject_id();
        Page pgMajors = majorService.findAllByPage(pg, discipline_level, discipline_id, subject_id);
        log.info("---分页查询所有major" + pgMajors.getRecords());
        return ResponseResult.SUCCESS().setData(pgMajors);
    }

    @GetMapping("/listAll/{subjectId}")
    public ResponseResult getAllMajorBySubject(@PathVariable("subjectId") String subjectId){
        QueryWrapper<InfoMajor> qw = new QueryWrapper<>();
        List<InfoMajor> majors = majorService.list(qw.eq("major_subject",subjectId));
        log.info("---根据一级学科查询所有专业"+majors);
        return ResponseResult.SUCCESS().setData(majors);
    }

    @PostMapping("/add")
    public ResponseResult addMajor(@RequestBody InfoMajor addMajor){
        QueryWrapper<InfoMajor> qw = new QueryWrapper<>();
        InfoMajor major = majorService.getOne(qw.eq("major_id",addMajor.getMajor_id()));
        if (major==null){
            boolean result = majorService.save(addMajor);
            if (result){
                log.info("---新增专业"+addMajor);
                return ResponseResult.SUCCESS().setData(addMajor);
            }
            else {
                log.info("---新增专业失败"+addMajor);
                return ResponseResult.FAILED("新增失败");
            }
        }
        else {
            log.info("---存在同编号专业，请勿重复添加");
            return ResponseResult.FAILED("专业id已存在，请勿重复添加!");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateMajor(@RequestBody InfoMajor updateMajor){
        boolean result = majorService.updateById(updateMajor);
        if (result){
            log.info("---修改专业信息"+updateMajor);
            return ResponseResult.SUCCESS();
        }
        else {
            log.info("---修改失败"+updateMajor);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{majorCode}")
    public ResponseResult deleteMajor(@PathVariable("majorCode") String majorCode){
        InfoMajor major = majorService.getById(majorCode);
        if (major!=null){
            boolean result = majorService.removeById(majorCode);
            if (result){
                log.info("---删除专业成功"+majorCode);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---删除失败"+majorCode);
                return ResponseResult.FAILED("删除失败");
            }
        }
        else {
            log.info("---专业编号不存在");
            return ResponseResult.FAILED("专业编号不存在");
        }
    }
}
