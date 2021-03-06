package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoPc;
import com.hust.gaokao_data_analysis_system.service.impl.InfoPcServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pc")
@Log4j
public class PcController {

    private InfoPcServiceImpl pcService;

    @Autowired
    public void setPcService(InfoPcServiceImpl pcService){
        this.pcService = pcService;
    }

    @PostMapping("/add")
    public ResponseResult pcAdd(@RequestBody InfoPc addPc){
        // 判断添加的批次是否存在
        QueryWrapper<InfoPc> qw = new QueryWrapper<>();
        qw.eq("pc_name", addPc.getPc_name());
        InfoPc pc = pcService.getOne(qw);
        if (pc == null){
            boolean result = pcService.save(addPc);
            if (result) {
                log.info("---新增批次成功" + addPc);
                return ResponseResult.SUCCESS().setData(addPc);
            } else {
                log.info("---新增批次失败" + addPc);
                return ResponseResult.FAILED("新增批次失败");
            }
        }else {
            log.info("---该批次已经存在此表中"+addPc);
            return ResponseResult.FAILED("该批次已经存在！请勿重复添加");
        }
    }

    @DeleteMapping("/del/{pc_code}")
    public ResponseResult pcDelete(@PathVariable("pc_code") long pcid){
        boolean result = pcService.removeById(pcid);
        if (result) {
            log.info("批次id：" + pcid + "---被删除");
            return ResponseResult.SUCCESS();
        } else {
            log.info("批次id：" + pcid + "---删除失败");
            return ResponseResult.FAILED("删除失败");
        }
    }

    @PostMapping("/update")
    public ResponseResult pcUpdate(@RequestBody InfoPc updatePc){
        boolean result = pcService.updateById(updatePc);
        if (result) {
            log.info(updatePc + "---修改成功");
            return ResponseResult.SUCCESS().setData(updatePc);
        } else {
            log.info(updatePc + "---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @PostMapping("/list")
    public ResponseResult getPcOnePage(@RequestBody PageRequest pageRequest){
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage,pageSize);
        Page pagePcs = pcService.page(pg);
        log.info(pagePcs.getRecords()+"---分页查询所有批次");
        return ResponseResult.SUCCESS().setData(pagePcs);
    }

    @GetMapping("/listAll")
    public ResponseResult getAllPc(){
        List<InfoPc> pcs = pcService.list();
        if(!pcs.isEmpty()){
            log.info(pcs+"---查询所有批次");
            return ResponseResult.SUCCESS().setData(pcs);
        }else {
            log.info("---批次表无数据");
            return ResponseResult.FAILED("---批次表无数据");
        }
    }
}
