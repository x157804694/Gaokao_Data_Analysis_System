package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.ZsOrLQSchoolMajorDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.ZsSchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.ZsSchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.ZsSchoolMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zsSchoolMajor")
@Log4j
public class ZsSchoolMajorController {
    private ZsSchoolMajorServiceImpl zsSchoolMajorService;

    @Autowired
    public void setZsSchoolMajorService(ZsSchoolMajorServiceImpl zsSchoolMajorService){
        this.zsSchoolMajorService = zsSchoolMajorService;
    }

    @RequestMapping("/list")
    public ResponseResult getAllZsSchoolMajorByPage(@RequestBody ZsOrLQSchoolMajorDTO zsSchoolMajorDTO){
        int currentPage = zsSchoolMajorDTO.getCurrentPage();
        int pageSize = zsSchoolMajorDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        // 查询条件
        int schoolId = zsSchoolMajorDTO.getSchool_id();
        int provinceCode = zsSchoolMajorDTO.getProvince_code();
        int zslxCode = zsSchoolMajorDTO.getZslx_code();
        int pcCode = zsSchoolMajorDTO.getPc_code();
        String year = zsSchoolMajorDTO.getYear();
        // 分页查询
        Page<ZsSchoolMajorVo> zsSchoolMajorVoPage = zsSchoolMajorService.findAllByPage(pg,schoolId,provinceCode,year,zslxCode,pcCode);
        log.info("---分页查询所有学校专业招生计划"+zsSchoolMajorVoPage.getRecords());
        return ResponseResult.SUCCESS().setData(zsSchoolMajorVoPage);
    }

    @RequestMapping("/listAll/{schoolId}")
    public ResponseResult getAllZsSchoolMajorBySchool(@PathVariable("schoolId") long schoolId){
        List<ZsSchoolMajorVo> zsSchoolMajorVoList = zsSchoolMajorService.findAll(schoolId);
        log.info("---查询某学校专业招生计划"+zsSchoolMajorVoList);
        return ResponseResult.SUCCESS().setData(zsSchoolMajorVoList);
    }

    @RequestMapping("/add")
    public ResponseResult addZsSchoolMajor(@RequestBody ZsSchoolMajor addZsSchoolMajor){
        QueryWrapper<ZsSchoolMajor> qw = new QueryWrapper<>();
        qw.eq("zs_school_major_school", addZsSchoolMajor.getZs_school_major_school());
        qw.eq("zs_school_major_major", addZsSchoolMajor.getZs_school_major_major());
        qw.eq("zs_school_major_province", addZsSchoolMajor.getZs_school_major_province());
        qw.eq("zs_school_major_year", addZsSchoolMajor.getZs_school_major_year());
        ZsSchoolMajor zsSchoolMajor = zsSchoolMajorService.getOne(qw);
        if (zsSchoolMajor==null){
            boolean result = zsSchoolMajorService.save(addZsSchoolMajor);
            if (result){
                log.info("---新增学校专业招生计划"+addZsSchoolMajor);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---新增失败"+addZsSchoolMajor);
                return ResponseResult.FAILED("新增失败");
            }
        }
        else {
            log.info("---该学校在当前年份、当前省份针对此专业已存在招生计划");
            return ResponseResult.FAILED("该学校在当前年份、当前省份针对此专业已存在招生计划,请勿重复添加");
        }
    }

    @RequestMapping("/update")
    public ResponseResult updateZsSchoolMajor(@RequestBody ZsSchoolMajor updateZsSchoolMajor){
        boolean reulst = zsSchoolMajorService.updateById(updateZsSchoolMajor);
        if (reulst){
            log.info("---修改成功"+updateZsSchoolMajor);
            return ResponseResult.SUCCESS();
        }
        else {
            log.info("---修改失败"+updateZsSchoolMajor);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{zsSchoolMajorCode}")
    public ResponseResult deleteZsSchoolMajor(@PathVariable("zsSchoolMajorCode") long zsSchoolMajorCode){
        ZsSchoolMajor zsSchoolMajor = zsSchoolMajorService.getById(zsSchoolMajorCode);
        if (zsSchoolMajor!=null){
            boolean result = zsSchoolMajorService.removeById(zsSchoolMajorCode);
            if (result){
                log.info("---删除成功"+zsSchoolMajorCode);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---删除失败"+zsSchoolMajorCode);
                return ResponseResult.FAILED();
            }
        }
        else {
            log.info("---该学校专业招生计划不存在");
            return ResponseResult.FAILED("该学校专业招生计划不存在");
        }
    }
}
