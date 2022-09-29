package com.hust.gaokao_data_analysis_system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolInfoDTO;
import com.hust.gaokao_data_analysis_system.service.impl.InfoSchoolServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/listAll")
    public ResponseResult getAllSchool() {
        QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
        qw.orderByAsc("CONVERT(school_name USING gbk)");
        List<InfoSchool> schoolList = schoolService.list(qw);
        log.info("---查询所有学校信息" + schoolList);
        return ResponseResult.SUCCESS().setData(schoolList);
    }

    @PostMapping("/listByPage")
    public ResponseResult getAllSchoolByPage(@RequestBody PageRequest pageRequest) {
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
    public ResponseResult getAllSchoolByCols(@RequestBody SchoolInfoDTO schoolInfoDTO) {
        // 获取页面参数
        int currentPage = schoolInfoDTO.getCurrentPage();
        int pageSize = schoolInfoDTO.getPageSize();
        //分页
        Page pg = new Page<>(currentPage, pageSize);
        QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
        // 参数对象转为map
        Map<String, String> schoolInfoVoMap = new HashMap<String, String>();
        schoolInfoVoMap = JSON.parseObject(JSON.toJSONString(schoolInfoDTO), new TypeReference<Map<String, String>>() {
        });
        // 移除分页参数
        schoolInfoVoMap.remove("pageSize");
        schoolInfoVoMap.remove("currentPage");
        // 转换筛选条件：双一流
        if (schoolInfoDTO.getSchool_dual() == 2) {
            schoolInfoVoMap.remove("school_dual");
        }
        if (schoolInfoDTO.getSchool_qj() == 2) {
            schoolInfoVoMap.remove("school_qj");
        }
        if (schoolInfoDTO.getSchool_sg() == 2) {
            schoolInfoVoMap.remove("school_sg");
        }
        // 转换筛选条件：（学校类型：所有：0 双非：1 211：2 985：3）
        schoolInfoVoMap.remove("school_class");
        int schoolClass = schoolInfoDTO.getSchool_class();
        if (schoolClass == 1) {
            schoolInfoVoMap.put("school_211", "0");
            schoolInfoVoMap.put("school_985", "0");
        } else if (schoolClass == 2) {
            schoolInfoVoMap.put("school_211", "1");
        } else if (schoolClass == 3) {
            schoolInfoVoMap.put("school_985", "1");
        }
        System.out.println("-----查询条件" + schoolInfoVoMap);
        // 筛选
        qw.allEq(schoolInfoVoMap, false);
        Page pageSchools = schoolService.page(pg, qw);
        log.info("---根据各类条件，分页查询所有学校信息" + pageSchools.getRecords());
        return ResponseResult.SUCCESS().setData(pageSchools);
    }

    @PostMapping("/add")
    public ResponseResult addSchool(@RequestBody InfoSchool addSchool) {
        // 判断是否存在重复的学校名和id
        QueryWrapper<InfoSchool> qw1 = new QueryWrapper<>();
        QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
        qw1.eq("school_id", addSchool.getSchool_id());
        qw2.eq("school_name", addSchool.getSchool_name());
        InfoSchool school1 = schoolService.getOne(qw1);
        InfoSchool school2 = schoolService.getOne(qw2);
        if (school1 != null) {
            return ResponseResult.FAILED("存在同id学校，请勿重复添加！");
        }
        if (school2 != null) {
            return ResponseResult.FAILED("存在同名学校，请勿重复添加！");
        }
        boolean result = schoolService.save(addSchool);
        if (result) {
            log.info(addSchool + "---新增学校成功");
            return ResponseResult.SUCCESS().setData(addSchool);
        } else {
            log.info(addSchool + "---新增学校失败");
            return ResponseResult.FAILED("新增学校失败");
        }
    }

    @PostMapping("/addBatch")
    public ResponseResult addSchoolOnBatch(@RequestBody List<InfoSchool> schoolList) {
        int result = schoolService.addBatch(schoolList);
        if (result > 0) {
            log.info("---批量新增学校成功" + schoolList);
            return ResponseResult.SUCCESS();
        } else {
            log.info("---批量新增学校失败" + schoolList);
            return ResponseResult.FAILED("批量新增学校失败");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchool(@RequestBody InfoSchool updateSchool) {
        boolean result = schoolService.updateById(updateSchool);
        if (result) {
            log.info("---修改学校信息成功" + updateSchool);
            return ResponseResult.SUCCESS().setData(updateSchool);
        } else {
            log.info("---修改学校信息失败" + updateSchool);
            return ResponseResult.FAILED("修改学校信息失败");
        }
    }

    @DeleteMapping("/delete/{schoolCode}")
    public ResponseResult deleteSchool(@PathVariable("schoolCode") long schoolCode) {
        boolean result = schoolService.removeById(schoolCode);
        if (result) {
            log.info("---删除学校信息成功：" + schoolCode);
            return ResponseResult.SUCCESS();
        } else {
            log.info("---删除学校信息失败：" + schoolCode);
            return ResponseResult.FAILED("删除学校信息失败");
        }
    }


}
