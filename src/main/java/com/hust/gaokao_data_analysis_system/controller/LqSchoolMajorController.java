package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.ZsOrLQSchoolMajorDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.LqSchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.vo.LqSchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.LqSchoolMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lqSchoolMajor")
@Log4j
public class LqSchoolMajorController {
    private LqSchoolMajorServiceImpl lqSchoolMajorService;

    @Autowired
    public void setLqSchoolMajorService(LqSchoolMajorServiceImpl lqSchoolMajorService) {
        this.lqSchoolMajorService = lqSchoolMajorService;
    }

    @RequestMapping("/list")
    public ResponseResult getAlllqSchoolMajorByPage(@RequestBody ZsOrLQSchoolMajorDTO zsSchoolMajorDTO){
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
        Page<LqSchoolMajorVo> lqSchoolMajorPage = lqSchoolMajorService.findAllByPage(pg,schoolId,provinceCode,year,zslxCode,pcCode);
        log.info("---分页查询所有学校专业录取分数线"+lqSchoolMajorPage.getRecords());
        return ResponseResult.SUCCESS().setData(lqSchoolMajorPage);
    }

    @RequestMapping("/listAll/{schoolId}")
    public ResponseResult getAlllqSchoolMajorBySchool(@PathVariable("schoolId") long schoolId){
        List<LqSchoolMajorVo> lqSchoolMajorVoList = lqSchoolMajorService.findAll(schoolId);
        log.info("---查询某学校专业录取分数线"+lqSchoolMajorVoList);
        return ResponseResult.SUCCESS().setData(lqSchoolMajorVoList);
    }

    @RequestMapping("/add")
    public ResponseResult addLqSchoolMajor(@RequestBody LqSchoolMajor addLqSchoolMajor){
        QueryWrapper<LqSchoolMajor> qw = new QueryWrapper<>();
        qw.eq("lq_school_major_school", addLqSchoolMajor.getLq_school_major_school());
        qw.eq("lq_school_major_major", addLqSchoolMajor.getLq_school_major_major());
        qw.eq("lq_school_major_province", addLqSchoolMajor.getLq_school_major_province());
        qw.eq("lq_school_major_year", addLqSchoolMajor.getLq_school_major_year());
        LqSchoolMajor lqSchoolMajor = lqSchoolMajorService.getOne(qw);
        if (lqSchoolMajor==null){
            boolean result = lqSchoolMajorService.save(addLqSchoolMajor);
            if (result){
                log.info("---新增专业录取分数线"+addLqSchoolMajor);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---新增专业录取分数线失败"+addLqSchoolMajor);
                return ResponseResult.FAILED("新增失败");
            }
        }
        else {
            log.info("---该学校在当前年份、当前省份针对此专业已录入录取分数线");
            return ResponseResult.FAILED("该学校在当前年份、当前省份针对此专业已录入录取分数线");
        }
    }

    @RequestMapping("/update")
    public ResponseResult updateLqSchoolMajor(@RequestBody LqSchoolMajor updateLqSchoolMajor){
        boolean result = lqSchoolMajorService.updateById(updateLqSchoolMajor);
        if (result){
            log.info("---修改成功"+updateLqSchoolMajor);
            return ResponseResult.SUCCESS();
        }
        else {
            log.info("---修改失败"+updateLqSchoolMajor);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{lqSchoolMajorCode}")
    public ResponseResult deleteLqSchoolMajor(@PathVariable("lqSchoolMajorCode") long lqSchoolMajorCode){
        LqSchoolMajor lqSchoolMajor = lqSchoolMajorService.getById(lqSchoolMajorCode);
        if (lqSchoolMajor!=null){
            boolean result = lqSchoolMajorService.removeById(lqSchoolMajorCode);
            if (result){
                log.info("---删除专业录取分数线"+lqSchoolMajor);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---删除失败"+lqSchoolMajor);
                return ResponseResult.FAILED("删除失败");
            }
        }
        else {
            log.info("---该学校此专业录取分数线不存在");
            return ResponseResult.FAILED("该学校此专业录取分数线不存在");
        }
    }
}
