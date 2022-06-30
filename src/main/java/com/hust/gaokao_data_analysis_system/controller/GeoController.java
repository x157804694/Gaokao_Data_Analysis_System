package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.GeoCity;
import com.hust.gaokao_data_analysis_system.pojo.entity.GeoProvince;
import com.hust.gaokao_data_analysis_system.pojo.entity.GeoRegion;
import com.hust.gaokao_data_analysis_system.service.impl.GeoCityServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.GeoProvinceServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.GeoRegionServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geo")
@Log4j
public class GeoController {
    private GeoRegionServiceImpl regionService;
    private GeoProvinceServiceImpl provinceService;
    private GeoCityServiceImpl cityService;

    @Autowired
    public void setRegionService(GeoRegionServiceImpl regionService) {
        this.regionService = regionService;
    }

    @Autowired
    public void setProvinceService(GeoProvinceServiceImpl provinceService) {
        this.provinceService = provinceService;
    }

    @Autowired
    public void setCityService(GeoCityServiceImpl cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/region/list")
    public ResponseResult getAllRegion(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page pageRegions = regionService.page(pg);
        log.info(pageRegions.getRecords() + "---分页查询所有区域");
        return ResponseResult.SUCCESS().setData(pageRegions);
    }

    @PostMapping("/region/add")
    public ResponseResult addRegion(@RequestBody GeoRegion addRegion) {
        // 判断是否存在同名区域
        QueryWrapper<GeoRegion> qw = new QueryWrapper<>();
        qw.eq("region_name", addRegion.getRegion_name());
        GeoRegion region = regionService.getOne(qw);
        if (region == null) {
            boolean result = regionService.save(addRegion);
            if (result) {
                log.info(addRegion + "---添加成功");
                return ResponseResult.SUCCESS().setData(addRegion);
            } else {
                log.info(addRegion + "---添加失败");
                return ResponseResult.FAILED("添加失败");
            }
        } else {
            log.info(addRegion + "---存在同名区域，添加失败");
            return ResponseResult.FAILED("存在同名区域，添加失败");
        }
    }

    @PostMapping("/region/update")
    public ResponseResult updateRegion(@RequestBody GeoRegion updateRegion) {
        boolean result = regionService.updateById(updateRegion);
        if (result) {
            log.info(updateRegion + "---修改成功");
            return ResponseResult.SUCCESS().setData(updateRegion);
        } else {
            log.info(updateRegion + "---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/region/delete/{regionCode}")
    public ResponseResult deleteRegion(@PathVariable("regionCode") long regionCode) {
        boolean result = regionService.removeById(regionCode);
        if (result) {
            log.info("Region:" + regionCode + "---删除成功");
            return ResponseResult.SUCCESS();
        } else {
            log.info("Region:" + regionCode + "---删除失败");
            return ResponseResult.FAILED("删除失败");
        }
    }

    @PostMapping("/province/list")
    public ResponseResult getAllProvince(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page pageProvinces = provinceService.page(pg);
        log.info(pageProvinces.getRecords() + "---分页查询所有省份");
        return ResponseResult.SUCCESS().setData(pageProvinces);
    }

    @PostMapping("/province/list/{provinceRegion}")
    public ResponseResult getAllProvinceByRegion(@RequestBody PageRequest pageRequest,@PathVariable("provinceRegion") long provinceRegion) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        QueryWrapper<GeoProvince> qw = new QueryWrapper<>();
        qw.eq("province_region",provinceRegion);
        Page pageProvinces = provinceService.page(pg,qw);
        log.info(pageProvinces.getRecords() + "---根据地区分页查询所有省份");
        return ResponseResult.SUCCESS().setData(pageProvinces);
    }

    @PostMapping("/province/add")
    public ResponseResult addProvince(@RequestBody GeoProvince addProvince) {
        // 判断是否存在同名区域
        QueryWrapper<GeoProvince> qw = new QueryWrapper<>();
        qw.eq("province_name", addProvince.getProvince_name());
        GeoProvince province = provinceService.getOne(qw);
        if (province == null) {
            boolean result = provinceService.save(addProvince);
            if (result) {
                log.info(addProvince + "---添加成功");
                return ResponseResult.SUCCESS().setData(addProvince);
            } else {
                log.info(addProvince + "---添加失败");
                return ResponseResult.FAILED("添加失败");
            }
        } else {
            log.info(addProvince + "---存在同名省份，添加失败");
            return ResponseResult.FAILED("存在同名省份，添加失败");
        }
    }

    @PostMapping("/province/update")
    public ResponseResult updateProvince(@RequestBody GeoProvince updateProvince) {
        boolean result = provinceService.updateById(updateProvince);
        if (result) {
            log.info(updateProvince + "---修改成功");
            return ResponseResult.SUCCESS().setData(updateProvince);
        } else {
            log.info(updateProvince + "---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/province/delete/{provinceCode}")
    public ResponseResult deleteProvince(@PathVariable("provinceCode") long provinceCode) {
        boolean result = provinceService.removeById(provinceCode);
        if (result) {
            log.info("Region:" + provinceCode + "---删除成功");
            return ResponseResult.SUCCESS();
        } else {
            log.info("Region:" + provinceCode + "---删除失败");
            return ResponseResult.FAILED("删除失败");
        }
    }

    @PostMapping("/city/list")
    public ResponseResult getAllCity(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page pageCities = cityService.page(pg);
        log.info(pageCities.getRecords() + "---分页查询所有城市");
        return ResponseResult.SUCCESS().setData(pageCities);
    }

    @PostMapping("/city/list/{cityProvince}")
    public ResponseResult getAllCityByProvince(@RequestBody PageRequest pageRequest,@PathVariable("cityProvince") long cityProvince) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        // 筛选省份
        QueryWrapper<GeoCity> qw = new QueryWrapper<>();
        qw.eq("city_province",cityProvince);
        Page pageCities = cityService.page(pg,qw);
        log.info(pageCities.getRecords() + "---根据省份分页查询所有城市");
        return ResponseResult.SUCCESS().setData(pageCities);
    }

    @PostMapping("/city/add")
    public ResponseResult addCity(@RequestBody GeoCity addCity) {
        // 判断是否存在同名区域
        QueryWrapper<GeoCity> qw = new QueryWrapper<>();
        qw.eq("city_name", addCity.getCity_name());
        GeoCity city = cityService.getOne(qw);
        if (city == null) {
            boolean result = cityService.save(addCity);
            if (result) {
                log.info(addCity + "---添加成功");
                return ResponseResult.SUCCESS().setData(addCity);
            } else {
                log.info(addCity + "---添加失败");
                return ResponseResult.FAILED("添加失败");
            }
        } else {
            log.info(addCity + "---存在同名城市，添加失败");
            return ResponseResult.FAILED("存在同名城市，添加失败");
        }
    }

    @PostMapping("/city/update")
    public ResponseResult updateCity(@RequestBody GeoCity updateCity) {
        boolean result = cityService.updateById(updateCity);
        if (result) {
            log.info(updateCity + "---修改成功");
            return ResponseResult.SUCCESS().setData(updateCity);
        } else {
            log.info(updateCity + "---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/city/delete/{cityCode}")
    public ResponseResult deleteCity(@PathVariable("cityCode") long cityCode) {
        GeoCity city = cityService.getById(cityCode);
        if (city!=null){
            boolean result = cityService.removeById(cityCode);
            if (result) {
                log.info("City:" + cityCode + "---删除成功");
                return ResponseResult.SUCCESS();
            } else {
                log.info("City:" + cityCode + "---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        }
        else {
            log.info("City" + cityCode + "---城市不存在");
            return ResponseResult.FAILED("城市不存在");
        }
    }

}
