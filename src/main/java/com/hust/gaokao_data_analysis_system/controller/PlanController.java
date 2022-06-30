package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanDual;
import com.hust.gaokao_data_analysis_system.service.impl.InfoSchoolServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.PlanDualServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.PlanQjServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.PlanSgServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan")
@Log4j
public class PlanController {
    private PlanDualServiceImpl dualService;
    private PlanQjServiceImpl qjService;
    private PlanSgServiceImpl sgService;
    private InfoSchoolServiceImpl schoolService;

    @Autowired
    public void setDualService(PlanDualServiceImpl dualService) {
        this.dualService = dualService;
    }

    @Autowired
    public void setQjService(PlanQjServiceImpl qjService) {
        this.qjService = qjService;
    }

    @Autowired
    public void setSgService(PlanSgServiceImpl sgService) {
        this.sgService = sgService;
    }

    @Autowired
    public void setSchoolService(InfoSchoolServiceImpl schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/dual/list")
    public ResponseResult getAllDualPlan(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page pageDualPlans = dualService.findAll(pg);
        log.info("---查询双一流高校名单" + pageDualPlans);
        return ResponseResult.SUCCESS().setData(pageDualPlans);
    }

    @PostMapping("/dual/add")
    public ResponseResult addDualPlan(@RequestBody PlanDual addDual) {
        // 判断添加的学校已存在
        QueryWrapper<PlanDual> qw = new QueryWrapper<>();
        QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
        qw.eq("dual_school", addDual.getDual_school());
        qw2.eq("school_id", addDual.getDual_school());
        PlanDual dual = dualService.getOne(qw);
        InfoSchool school = schoolService.getOne(qw2);
        if (dual == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            if (school != null) {
                boolean result = dualService.save(addDual);
                if (result) {
                    log.info("---新增双一流高校" + addDual);
                    return ResponseResult.SUCCESS().setData(addDual);
                } else {
                    log.info("---新增失败" + addDual);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校"+addDual);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该学校已经存在此表中"+addDual);
            return ResponseResult.FAILED("该学校已经存在！请勿重复添加");
        }
    }

    @PostMapping("/dual/update")
    public ResponseResult updateDualPlan(@RequestBody PlanDual updateDual) {
        boolean result = dualService.updateById(updateDual);
        if (result) {
            log.info("---修改成功" + updateDual);
            return ResponseResult.SUCCESS().setData(updateDual);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/dual/delete/{dualCode}")
    public ResponseResult deleteDualPlan(@PathVariable("dualCode") long dualCode) {
        boolean result = dualService.removeById(dualCode);
        if (result) {
            log.info("---删除双一流学校成功");
            return ResponseResult.SUCCESS();
        } else {
            log.info("---删除失败");
            return ResponseResult.FAILED("删除失败");
        }
    }
}
