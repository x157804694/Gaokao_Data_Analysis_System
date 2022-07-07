package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoPc;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoZslx;
import com.hust.gaokao_data_analysis_system.service.impl.InfoZslxServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zslx")
@Log4j
public class ZslxController {
    private InfoZslxServiceImpl zslxService;

    @Autowired
    public void setZslxService(InfoZslxServiceImpl zslxService){
        this.zslxService = zslxService;
    }

    @PostMapping("/add")
    public ResponseResult zslxAdd(@RequestBody InfoZslx addZslx){
        // 判断添加的批次是否存在
        QueryWrapper<InfoZslx> qw = new QueryWrapper<>();
        qw.eq("zslx_code", addZslx.getZslx_code());
        InfoZslx zslx = zslxService.getOne(qw);
        if (zslx == null){
            boolean result = zslxService.save(addZslx);
            if (result) {
                log.info("---新增招生类型成功" + addZslx);
                return ResponseResult.SUCCESS().setData(addZslx);
            } else {
                log.info("---新增招生类型失败" + addZslx);
                return ResponseResult.FAILED("新增招生类型失败");
            }
        }else {
            log.info("---该招生类型已经存在此表中"+addZslx);
            return ResponseResult.FAILED("该招生类型已经存在！请勿重复添加");
        }
    }

    @DeleteMapping("/del/{zslx_code}")
    public ResponseResult zslxDelete(@PathVariable("zslx_code") long zslxid){
        boolean result = zslxService.removeById(zslxid);
        if (result) {
            log.info("招生类型id：" + zslxid + "---被删除");
            return ResponseResult.SUCCESS();
        } else {
            log.info("招生类型id：" + zslxid + "---删除失败");
            return ResponseResult.FAILED("删除失败");
        }
    }

    @PostMapping("/update")
    public ResponseResult zslxUpdate(@RequestBody InfoZslx updateZslx){
        boolean result = zslxService.updateById(updateZslx);
        if (result) {
            log.info(updateZslx + "---修改成功");
            return ResponseResult.SUCCESS().setData(updateZslx);
        } else {
            log.info(updateZslx + "---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @PostMapping("/list")
    public ResponseResult getAllZslx(@RequestBody PageRequest pageRequest){
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage,pageSize);
        Page pageZslxs = zslxService.page(pg);
        log.info(pageZslxs.getRecords()+"---分页查询所有招生类型");
        return ResponseResult.SUCCESS().setData(pageZslxs);
    }

    @PostMapping("/listall")
    public ResponseResult getAllZslxs(){
        List<InfoZslx> zslxes = zslxService.list();
        if(!zslxes.isEmpty()){
            log.info(zslxes+"---查询所有招生类型");
            return ResponseResult.SUCCESS().setData(zslxes);
        }else {
            log.info("---招生类型表无数据");
            return ResponseResult.FAILED("---招生类型表无数据");
        }
    }
}
