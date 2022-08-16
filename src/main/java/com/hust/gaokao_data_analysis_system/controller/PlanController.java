package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.PlanDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoSchool;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanDual;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanQj;
import com.hust.gaokao_data_analysis_system.pojo.entity.PlanSg;
import com.hust.gaokao_data_analysis_system.service.impl.InfoSchoolServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.PlanDualServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.PlanQjServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.PlanSgServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseResult getAllDualPlan(@RequestBody PlanDTO planDTO) {
        int currentPage = planDTO.getCurrentPage();
        int pageSize = planDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = planDTO.getDual_year();
        Page pageDualPlans = dualService.findAll(pg, year);
        log.info("---查询双一流高校名单" + pageDualPlans);
        return ResponseResult.SUCCESS().setData(pageDualPlans);
    }

    @PostMapping("/dual/add")
    public ResponseResult addDualPlan(@RequestBody PlanDual addDual) {
        // 判断添加的学校已存在
        QueryWrapper<PlanDual> qw = new QueryWrapper<>();
        qw.eq("dual_year", addDual.getDual_year());
        qw.eq("dual_school", addDual.getDual_school());
        PlanDual dual = dualService.getOne(qw);
        if (dual == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addDual.getDual_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = dualService.save(addDual);
                if (result) {
                    log.info("---新增双一流高校" + addDual);
                    // 给该高校信息中的school_dual字段修改为1
                    school.setSchool_dual(1);
                    schoolService.updateById(school);
                    return ResponseResult.SUCCESS().setData(addDual);
                } else {
                    log.info("---新增失败" + addDual);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addDual);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---已有该学校在该年为双一流的记录" + addDual);
            return ResponseResult.FAILED("已有该学校在该年为双一流的记录！请勿重复添加");
        }
    }

    @PostMapping("/dual/update")
    public ResponseResult updateDualPlan(@RequestBody PlanDual updateDual) {
        // 判断添加的学校已存在
        QueryWrapper<PlanDual> qw = new QueryWrapper<>();
        qw.eq("dual_year", updateDual.getDual_year());
        qw.eq("dual_school", updateDual.getDual_school());
        PlanDual dual = dualService.getOne(qw);
        if (dual == null) {
            boolean result = dualService.updateById(updateDual);
            if (result) {
                log.info("---修改成功" + updateDual);
                return ResponseResult.SUCCESS().setData(updateDual);
            } else {
                log.info("---修改失败");
                return ResponseResult.FAILED("修改失败");
            }
        }
        else {
            log.info("---已有该学校在该年为双一流的记录" + updateDual);
            return ResponseResult.FAILED("已有该学校在该年为双一流的记录！请勿重复添加");
        }
    }

    @DeleteMapping("/dual/delete/{dualCode}")
    public ResponseResult deleteDualPlan(@PathVariable("dualCode") long dualCode) {
        // 根据code获取实例对象
        PlanDual dual = dualService.getById(dualCode);
        if (dual != null) {
            boolean result = dualService.removeById(dualCode);
            if (result) {
                log.info("---删除双一流信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", dual.getDual_school());
                InfoSchool school = schoolService.getOne(qw);
                // 给该高校信息中的school_dual字段修改为0
                school.setSchool_dual(0);
                schoolService.updateById(school);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---双一流code不存在");
            return ResponseResult.FAILED("双一流code不存在");
        }
    }

    @GetMapping("/dual/getAllYears")
    public ResponseResult getDualAllYears() {
        QueryWrapper<PlanDual> qw = new QueryWrapper<>();
        qw.select("distinct dual_year");
        List<Object> years = dualService.listObjs(qw);
        log.info("---查询双一流计划所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }


    @PostMapping("/qj/list")
    public ResponseResult getAllQjPlan(@RequestBody PlanDTO planDTO) {
        int currentPage = planDTO.getCurrentPage();
        int pageSize = planDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = planDTO.getQj_year();
        Page pageDualPlans = qjService.findAll(pg, year);
        log.info("---分页查询强基计划高校名单" + pageDualPlans);
        return ResponseResult.SUCCESS().setData(pageDualPlans);
    }

    @PostMapping("/qj/add")
    public ResponseResult addQjPlan(@RequestBody PlanQj addQj) {
        // 判断添加的学校已存在
        QueryWrapper<PlanQj> qw = new QueryWrapper<>();
        qw.eq("qj_school", addQj.getQj_school());
        PlanQj qj = qjService.getOne(qw);
        if (qj == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addQj.getQj_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = qjService.save(addQj);
                if (result) {
                    log.info("---新增强基计划高校" + addQj);
                    // 给该高校信息中的school_dual字段修改为1
                    school.setSchool_qj(1);
                    schoolService.updateById(school);
                    return ResponseResult.SUCCESS().setData(addQj);
                } else {
                    log.info("---新增失败" + addQj);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addQj);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该学校已经存在此表中" + addQj);
            return ResponseResult.FAILED("该学校已经存在！请勿重复添加");
        }
    }

    @PostMapping("/qj/update")
    public ResponseResult updateQjPlan(@RequestBody PlanQj updateQj) {
        boolean result = qjService.updateById(updateQj);
        if (result) {
            log.info("---修改成功" + updateQj);
            return ResponseResult.SUCCESS().setData(updateQj);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/qj/delete/{qjCode}")
    public ResponseResult deleteQjPlan(@PathVariable("qjCode") long qjCode) {
        // 根据code获取实例对象
        PlanQj qj = qjService.getById(qjCode);
        if (qj != null) {
            boolean result = qjService.removeById(qjCode);
            if (result) {
                log.info("---删除强基计划信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", qj.getQj_school());
                InfoSchool school = schoolService.getOne(qw);
                // 给该高校信息中的school_qj字段修改为0
                school.setSchool_qj(0);
                schoolService.updateById(school);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---强基计划code不存在");
            return ResponseResult.FAILED("强基计划code不存在");
        }
    }

    @GetMapping("/qj/getAllYears")
    public ResponseResult getQjAllYears() {
        QueryWrapper<PlanQj> qw = new QueryWrapper<>();
        qw.select("distinct qj_year");
        List<Object> years = qjService.listObjs(qw);
        log.info("---查询双一流计划所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }

    @PostMapping("/sg/list")
    public ResponseResult getAllSgPlan(@RequestBody PlanDTO planDTO) {
        int currentPage = planDTO.getCurrentPage();
        int pageSize = planDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = planDTO.getSg_year();
        Page pageDualPlans = sgService.findAll(pg, year);
        log.info("---分页查询双高计划高校名单" + pageDualPlans);
        return ResponseResult.SUCCESS().setData(pageDualPlans);
    }

    @PostMapping("/sg/add")
    public ResponseResult addSgPlan(@RequestBody PlanSg addSg) {
        // 判断添加的学校已存在
        QueryWrapper<PlanSg> qw = new QueryWrapper<>();
        qw.eq("sg_school", addSg.getSg_school());
        PlanSg qj = sgService.getOne(qw);
        if (qj == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addSg.getSg_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = sgService.save(addSg);
                if (result) {
                    log.info("---新增双高计划高校" + addSg);
                    // 给该高校信息中的school_sg字段修改为1
                    school.setSchool_sg(1);
                    schoolService.updateById(school);
                    return ResponseResult.SUCCESS().setData(addSg);
                } else {
                    log.info("---新增失败" + addSg);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addSg);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该学校已经存在此表中" + addSg);
            return ResponseResult.FAILED("该学校已经存在！请勿重复添加");
        }
    }

    @PostMapping("/sg/update")
    public ResponseResult updateSgPlan(@RequestBody PlanSg updateSg) {
        boolean result = sgService.updateById(updateSg);
        if (result) {
            log.info("---修改成功" + updateSg);
            return ResponseResult.SUCCESS().setData(updateSg);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/sg/delete/{sgCode}")
    public ResponseResult deleteSGPlan(@PathVariable("sgCode") long sgCode) {
        // 根据code获取实例对象
        PlanSg sg = sgService.getById(sgCode);
        if (sg != null) {
            boolean result = sgService.removeById(sgCode);
            if (result) {
                log.info("---删除双高计划信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", sg.getSg_school());
                InfoSchool school = schoolService.getOne(qw);
                // 给该高校信息中的school_sg字段修改为0
                school.setSchool_sg(0);
                schoolService.updateById(school);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---双高计划code不存在");
            return ResponseResult.FAILED("双高计划code不存在");
        }
    }

    @GetMapping("/sg/getAllYears")
    public ResponseResult getSgAllYears() {
        QueryWrapper<PlanSg> qw = new QueryWrapper<>();
        qw.select("distinct sg_year");
        List<Object> years = sgService.listObjs(qw);
        log.info("---查询双一流计划所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }
}
