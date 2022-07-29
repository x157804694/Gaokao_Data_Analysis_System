package com.hust.gaokao_data_analysis_system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.ProvinceControlDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoProvinceControl;
import com.hust.gaokao_data_analysis_system.service.impl.InfoProvinceControlServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provinceControl")
@Log4j
public class ProvinceControlController {
    private InfoProvinceControlServiceImpl provinceControlService;

    @Autowired
    public void setProvinceControlService(InfoProvinceControlServiceImpl provinceControlService) {
        this.provinceControlService = provinceControlService;
    }

    @PostMapping("/listByPage")
    public ResponseResult getAllProvinceControlByPage(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page<InfoProvinceControl> infoProvinceControlPage = provinceControlService.page(pg);
        log.info("---分页查询所有省控线" + infoProvinceControlPage.getRecords());
        return ResponseResult.SUCCESS().setData(infoProvinceControlPage);
    }

    @PostMapping("/list")
    public ResponseResult getAllByCols(@RequestBody ProvinceControlDTO provinceControlDTO) {
        int currentPage = provinceControlDTO.getCurrentPage();
        int pageSize = provinceControlDTO.getPageSize();
        // 分页
        Page pg = new Page<>(currentPage, pageSize);
        QueryWrapper<InfoProvinceControl> qw = new QueryWrapper<>();
        // 参数对象转为map
        Map<String, String> provinceControlMap = new HashMap<String, String>();
        provinceControlMap = JSON.parseObject(JSON.toJSONString(provinceControlDTO), new TypeReference<Map<String, String>>() {
        });
        provinceControlMap.remove("pageSize");
        provinceControlMap.remove("currentPage");
        System.out.println("-----查询条件" + provinceControlMap);
        // 筛选
        qw.allEq(provinceControlMap, false);
        Page<InfoProvinceControl> provinceControlPage = provinceControlService.page(pg, qw);
        log.info("---根据各类条件，分页查询省控线信息" + provinceControlPage.getRecords());
        return ResponseResult.SUCCESS().setData(provinceControlPage);
    }

    @PostMapping("/getOneByCols")
    public ResponseResult getOneByCols(@RequestBody ProvinceControlDTO provinceControlDTO){
        // 参数对象转为map
        Map<String, String> provinceControlMap = new HashMap<String, String>();
        provinceControlMap = JSON.parseObject(JSON.toJSONString(provinceControlDTO), new TypeReference<Map<String, String>>() {
        });
        provinceControlMap.remove("pageSize");
        provinceControlMap.remove("currentPage");
        System.out.println("-----查询条件" + provinceControlMap);
        QueryWrapper<InfoProvinceControl> qw = new QueryWrapper<>();
        qw.allEq(provinceControlMap, false);
        InfoProvinceControl provinceControl = provinceControlService.getOne(qw);
        log.info("---根据省份、年份、批次、招生类型查询省控线"+provinceControl);
        return ResponseResult.SUCCESS().setData(provinceControl);
    }

    @PostMapping("/add")
    public ResponseResult addProvinceControl(@RequestBody InfoProvinceControl addProvinceControl){
        // 判断是否存在同省份同年份同招生类型同批次
        QueryWrapper<InfoProvinceControl> qw = new QueryWrapper<>();
        qw.eq("province_control_province", addProvinceControl.getProvince_control_province());
        qw.eq("province_control_zslx", addProvinceControl.getProvince_control_zslx());
        qw.eq("province_control_pc", addProvinceControl.getProvince_control_pc());
        qw.eq("province_control_year", addProvinceControl.getProvince_control_year());
        InfoProvinceControl infoProvinceControl = provinceControlService.getOne(qw);
        if (infoProvinceControl==null){
            boolean result = provinceControlService.save(addProvinceControl);
            if (result){
                log.info("---新增省控线"+addProvinceControl);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---新增省控线失败"+addProvinceControl);
                return ResponseResult.FAILED("新增省控线失败");
            }
        }
        else {
            log.info("---当前省份在该年该招生类型该批次已存在招生计划");
            return ResponseResult.FAILED("当前省份在该年该招生类型该批次已存在招生计划，请勿重复添加");
        }

    }

    @PostMapping("/update")
    public ResponseResult updateProvinceControl(@RequestBody InfoProvinceControl updateProvinceControl){
        boolean result = provinceControlService.updateById(updateProvinceControl);
        if (result){
            log.info("---修改成功"+updateProvinceControl);
            return ResponseResult.SUCCESS().setData(updateProvinceControl);
        }
        else {
            log.info("---修改失败"+updateProvinceControl);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{provinceControlCode}")
    public ResponseResult deleteProvinceControl(@PathVariable("provinceControlCode") long provinceControlCode){
        InfoProvinceControl infoProvinceControl = provinceControlService.getById(provinceControlCode);
        if (infoProvinceControl!=null){
            boolean result = provinceControlService.removeById(provinceControlCode);
            if (result){
                log.info("---删除成功"+provinceControlCode);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---删除失败"+provinceControlCode);
                return ResponseResult.FAILED("删除失败");
            }
        }
        else {
            log.info("---该省控线不存在");
            return ResponseResult.FAILED("该省控线不存在");
        }
    }

    @GetMapping("/getYearByProvince/{provinceName}")
    public ResponseResult getYearByProvince(@PathVariable("provinceName") String provinceName){
        List<String> yearList = provinceControlService.getYearsByProvince(provinceName);
        log.info("---根据省份查询省控线的年份"+yearList);
        return ResponseResult.SUCCESS().setData(yearList);
    }
}
