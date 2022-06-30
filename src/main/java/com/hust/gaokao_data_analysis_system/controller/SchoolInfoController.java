package com.hust.gaokao_data_analysis_system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolInfoQueryDTO;
import com.hust.gaokao_data_analysis_system.service.impl.InfoSchoolServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/school")
@Log4j
public class SchoolInfoController {
    private InfoSchoolServiceImpl schoolService;

    @Autowired
    public void setSchoolService(InfoSchoolServiceImpl schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/listAll")
    public ResponseResult getAllSchool(@RequestBody PageRequest pageRequest) {
        // 获取页面参数
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        //分页
        Page pg = new Page<>(currentPage, pageSize);
        Page pageSchools = schoolService.page(pg);
        log.info(pageSchools.getRecords() + "---分页查询所有学校信息");
        return ResponseResult.SUCCESS().setData(pageSchools);
    }

    @PostMapping("/list")
    public ResponseResult getAllSchoolByCols(@RequestBody SchoolInfoQueryDTO schoolInfoQueryDTO) {
        // 获取页面参数
        int currentPage = schoolInfoQueryDTO.getCurrentPage();
        int pageSize = schoolInfoQueryDTO.getPageSize();
        //分页
        Page pg = new Page<>(currentPage, pageSize);
        QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
        // 参数对象转为map
        Map<String, String> schoolInfoVoMap = new HashMap<String, String>();
        schoolInfoVoMap = JSON.parseObject(JSON.toJSONString(schoolInfoQueryDTO), new TypeReference<Map<String, String>>() {
        });
        // 移除分页参数
        schoolInfoVoMap.remove("pageSize");
        schoolInfoVoMap.remove("currentPage");
        // 筛选
        qw.allEq(schoolInfoVoMap,false);
        Page pageSchools = schoolService.page(pg,qw);
        log.info(schoolInfoQueryDTO + "---筛选条件");
        log.info(pageSchools.getRecords() + "---根据各类条件，分页查询所有学校信息");
        return ResponseResult.SUCCESS().setData(pageSchools);
    }

    @PostMapping("/add")
    public ResponseResult addSchool(@RequestBody InfoSchool addSchool) {
        // 判断是否存在同名
        QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
        qw.eq("school_id", addSchool.getSchool_id());
        InfoSchool school = schoolService.getOne(qw);
        if (school == null) {
            boolean result = schoolService.save(addSchool);
            if (result) {
                log.info(addSchool + "---新增学校成功");
                return ResponseResult.SUCCESS().setData(addSchool);
            } else {
                log.info(addSchool + "---新增学校失败");
                return ResponseResult.FAILED("新增学校失败");
            }
        } else {
            log.info(addSchool + "---学校id已存在");
            return ResponseResult.FAILED("学校id已存在，请勿重复添加");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchool(@RequestBody InfoSchool updateSchool) {
        boolean result = schoolService.updateById(updateSchool);
        if (result) {
            log.info(updateSchool + "---修改学校信息成功");
            return ResponseResult.SUCCESS().setData(updateSchool);
        } else {
            log.info(updateSchool + "---修改学校信息失败");
            return ResponseResult.FAILED("修改学校信息失败");
        }
    }

    @DeleteMapping("/delete/{schoolCode}")
    public ResponseResult deleteSchool(@PathVariable("schoolCode") long schoolCode) {
        boolean result = schoolService.removeById(schoolCode);
        if (result) {
            log.info("学校：" + schoolCode + "---删除成功");
            return ResponseResult.SUCCESS();
        } else {
            log.info("学校：" + schoolCode + "---删除失败");
            return ResponseResult.FAILED("修改学校信息失败");
        }
    }


}
