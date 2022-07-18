package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.RankDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.*;
import com.hust.gaokao_data_analysis_system.service.impl.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rank")
@Log4j
public class RankController {
    private RankQsServiceImpl qsService;
    private RankRkServiceImpl rkService;
    private RankXyhServiceImpl xyhService;
    private RankWslServiceImpl wslService;
    private RankUsServiceImpl usService;
    private RankTwsServiceImpl twsService;
    private InfoSchoolServiceImpl schoolService;

    @Autowired
    public void setQsService(RankQsServiceImpl qsService) {
        this.qsService = qsService;
    }

    @Autowired
    public void setRkService(RankRkServiceImpl rkService) {
        this.rkService = rkService;
    }

    @Autowired
    public void setXyhService(RankXyhServiceImpl xyhService) {
        this.xyhService = xyhService;
    }

    @Autowired
    public void setWslService(RankWslServiceImpl wslService) {
        this.wslService = wslService;
    }

    @Autowired
    public void setUsService(RankUsServiceImpl usService) {
        this.usService = usService;
    }

    @Autowired
    public void setTwsService(RankTwsServiceImpl twsService) {
        this.twsService = twsService;
    }

    @Autowired
    public void setSchoolService(InfoSchoolServiceImpl schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/qs/add")
    public ResponseResult addQsRank(@RequestBody RankQs addQs) {
        //判断添加的qs排名已存在
        QueryWrapper<RankQs> qw = new QueryWrapper<>();
        qw.eq("qs_school", addQs.getQs_school()).eq("qs_year", addQs.getQs_year());
        RankQs qs = qsService.getOne(qw);
        if (qs == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addQs.getQs_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = qsService.save(addQs);
                if (result) {
                    log.info("---新增Qs排名" + addQs);
                    // 根据是否最新修改school里面的最新排名字段
                    Integer schoolRank = school.getSchool_qs();
                    if (schoolRank != null) {
                        //需要判断是否为最新排名
                        RankQs newest = qsService.findNewest(school.getSchool_id());
                        if (Integer.parseInt(addQs.getQs_year()) == Integer.parseInt(newest.getQs_year())) {
                            //表明是最新的
                            school.setSchool_qs(addQs.getQs_rank());
                            schoolService.updateById(school);
                        }
                    } else {
                        school.setSchool_qs(addQs.getQs_rank());
                        schoolService.updateById(school);
                    }
                    return ResponseResult.SUCCESS().setData(addQs);
                } else {
                    log.info("---新增失败" + addQs);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addQs);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该Qs排名已经存在此表中" + addQs);
            return ResponseResult.FAILED("该Qs排名已经存在！请勿重复添加");
        }
    }

    @PostMapping("/qs/list")
    public ResponseResult getAllQsRank(@RequestBody RankDTO rankDTO) {
        int currentPage = rankDTO.getCurrentPage();
        int pageSize = rankDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = rankDTO.getQs_year();
        Page pageQsRanks = qsService.findAll(pg, year);
        log.info("---查询Qs排名" + pageQsRanks);
        return ResponseResult.SUCCESS().setData(pageQsRanks);
    }

    @PostMapping("/qs/listAll")
    public ResponseResult getAllQss() {
        List<RankQs> qss = qsService.list();
        if (!qss.isEmpty()) {
            log.info(qss + "---查询所有Qs排名");
            return ResponseResult.SUCCESS().setData(qss);
        } else {
            log.info("---Qs排名表无数据");
            return ResponseResult.FAILED("---Qs排名表无数据");
        }
    }

    @PostMapping("/qs/update")
    public ResponseResult updateQsRank(@RequestBody RankQs updateQs) {
        boolean result = qsService.updateById(updateQs);
        if (result) {
            log.info("---修改成功" + updateQs);
            return ResponseResult.SUCCESS().setData(updateQs);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/qs/delete/{qsCode}")
    public ResponseResult deleteQsRank(@PathVariable("qsCode") long qsCode) {
        // 根据code获取实例对象
        RankQs qs = qsService.getById(qsCode);
        if (qs != null) {
            boolean result = qsService.removeById(qsCode);
            if (result) {
                log.info("---删除Qs排名信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", qs.getQs_school());
                InfoSchool school = schoolService.getOne(qw);
                RankQs newest = qsService.findNewest(school.getSchool_id());
                if (newest != null) {
                    //表明该学校还有排名记录
                    school.setSchool_qs(newest.getQs_rank());
                    schoolService.updateById(school);
                } else {
                    //没有记录则将学校表里的这一排名列置为null
                    school.setSchool_qs(null);
                    schoolService.updateById(school);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---Qscode不存在");
            return ResponseResult.FAILED("Qscode不存在");
        }
    }

    @GetMapping("/qs/getAllYears")
    public ResponseResult getAllQsYears() {
        QueryWrapper<RankQs> qw = new QueryWrapper<>();
        qw.select("distinct qs_year");
        List<Object> years = qsService.listObjs(qw);
        log.info("---查询qs排名所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }


    @PostMapping("/rk/add")
    public ResponseResult addRkRank(@RequestBody RankRk addRk) {
        //判断添加的rk排名已存在
        QueryWrapper<RankRk> qw = new QueryWrapper<>();
        qw.eq("rk_school", addRk.getRk_school()).eq("rk_year", addRk.getRk_year());
        RankRk rk = rkService.getOne(qw);
        if (rk == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addRk.getRk_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = rkService.save(addRk);
                if (result) {
                    log.info("---新增Rk排名" + addRk);
                    // 根据是否最新修改school里面的最新排名字段
                    Integer schoolRank = school.getSchool_rk();
                    if (schoolRank != null) {
                        //需要判断是否为最新排名
                        RankRk newest = rkService.findNewest(school.getSchool_id());
                        if (Integer.parseInt(addRk.getRk_year()) == Integer.parseInt(newest.getRk_year())) {
                            //表明是最新的
                            school.setSchool_rk(addRk.getRk_rank());
                            schoolService.updateById(school);
                        }
                    } else {
                        school.setSchool_rk(addRk.getRk_rank());
                        schoolService.updateById(school);
                    }
                    return ResponseResult.SUCCESS().setData(addRk);
                } else {
                    log.info("---新增失败" + addRk);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addRk);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该Rk排名已经存在此表中" + addRk);
            return ResponseResult.FAILED("该Rk排名已经存在！请勿重复添加");
        }
    }

    @PostMapping("/rk/list")
    public ResponseResult getAllRkRank(@RequestBody RankDTO rankDTO) {
        int currentPage = rankDTO.getCurrentPage();
        int pageSize = rankDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = rankDTO.getRk_year();
        Page pageRkRanks = rkService.findAll(pg, year);
        log.info("---查询Rk排名" + pageRkRanks);
        return ResponseResult.SUCCESS().setData(pageRkRanks);
    }

    @PostMapping("/rk/listAll")
    public ResponseResult getAllRks() {
        List<RankRk> rks = rkService.list();
        if (!rks.isEmpty()) {
            log.info(rks + "---查询所有Rk排名");
            return ResponseResult.SUCCESS().setData(rks);
        } else {
            log.info("---Rk排名表无数据");
            return ResponseResult.FAILED("---Rk排名表无数据");
        }
    }

    @PostMapping("/rk/update")
    public ResponseResult updateRkRank(@RequestBody RankRk updateRk) {
        boolean result = rkService.updateById(updateRk);
        if (result) {
            log.info("---修改成功" + updateRk);
            return ResponseResult.SUCCESS().setData(updateRk);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/rk/delete/{rkCode}")
    public ResponseResult deleteRkRank(@PathVariable("rkCode") long rkCode) {
        // 根据code获取实例对象
        RankRk rk = rkService.getById(rkCode);
        if (rk != null) {
            boolean result = rkService.removeById(rkCode);
            if (result) {
                log.info("---删除Rk排名信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", rk.getRk_school());
                InfoSchool school = schoolService.getOne(qw);
                RankRk newest = rkService.findNewest(school.getSchool_id());
                if (newest != null) {
                    //表明该学校还有排名记录
                    school.setSchool_rk(newest.getRk_rank());
                    schoolService.updateById(school);
                } else {
                    //没有记录则将学校表里的这一排名列置为null
                    school.setSchool_rk(null);
                    schoolService.updateById(school);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---Rkcode不存在");
            return ResponseResult.FAILED("Rkcode不存在");
        }
    }

    @GetMapping("/rk/getAllYears")
    public ResponseResult getAllRkYears() {
        QueryWrapper<RankRk> qw = new QueryWrapper<>();
        qw.select("distinct rk_year");
        List<Object> years = rkService.listObjs(qw);
        log.info("---查询qs排名所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }

    @PostMapping("/tws/add")
    public ResponseResult addTwsRank(@RequestBody RankTws addTws) {
        //判断添加的tws排名已存在
        QueryWrapper<RankTws> qw = new QueryWrapper<>();
        qw.eq("tws_school", addTws.getTws_school()).eq("tws_year", addTws.getTws_year());
        RankTws tws = twsService.getOne(qw);
        if (tws == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addTws.getTws_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = twsService.save(addTws);
                if (result) {
                    log.info("---新增Tws排名" + addTws);
                    // 根据是否最新修改school里面的最新排名字段
                    Integer schoolRank = school.getSchool_tws();
                    if (schoolRank != null) {
                        //需要判断是否为最新排名
                        RankTws newest = twsService.findNewest(school.getSchool_id());
                        if (Integer.parseInt(addTws.getTws_year()) == Integer.parseInt(newest.getTws_year())) {
                            //表明是最新的
                            school.setSchool_tws(addTws.getTws_rank());
                            schoolService.updateById(school);
                        }
                    } else {
                        school.setSchool_tws(addTws.getTws_rank());
                        schoolService.updateById(school);
                    }
                    return ResponseResult.SUCCESS().setData(addTws);
                } else {
                    log.info("---新增失败" + addTws);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addTws);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该Tws排名已经存在此表中" + addTws);
            return ResponseResult.FAILED("该Tws排名已经存在！请勿重复添加");
        }
    }

    @PostMapping("/tws/list")
    public ResponseResult getAllTwsRank(@RequestBody RankDTO rankDTO) {
        int currentPage = rankDTO.getCurrentPage();
        int pageSize = rankDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = rankDTO.getTws_year();
        Page pageTwsRanks = twsService.findAll(pg,year);
        log.info("---查询Tws排名" + pageTwsRanks);
        return ResponseResult.SUCCESS().setData(pageTwsRanks);
    }

    @PostMapping("/tws/listAll")
    public ResponseResult getAllTwss() {
        List<RankTws> twss = twsService.list();
        if (!twss.isEmpty()) {
            log.info(twss + "---查询所有Tws排名");
            return ResponseResult.SUCCESS().setData(twss);
        } else {
            log.info("---Tws排名表无数据");
            return ResponseResult.FAILED("---Tws排名表无数据");
        }
    }

    @PostMapping("/tws/update")
    public ResponseResult updateTwsRank(@RequestBody RankTws updateTws) {
        boolean result = twsService.updateById(updateTws);
        if (result) {
            log.info("---修改成功" + updateTws);
            return ResponseResult.SUCCESS().setData(updateTws);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/tws/delete/{twsCode}")
    public ResponseResult deleteTwsRank(@PathVariable("twsCode") long twsCode) {
        // 根据code获取实例对象
        RankTws tws = twsService.getById(twsCode);
        if (tws != null) {
            boolean result = twsService.removeById(twsCode);
            if (result) {
                log.info("---删除Tws排名信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", tws.getTws_school());
                InfoSchool school = schoolService.getOne(qw);
                RankTws newest = twsService.findNewest(school.getSchool_id());
                if (newest != null) {
                    //表明该学校还有排名记录
                    school.setSchool_tws(newest.getTws_rank());
                    schoolService.updateById(school);
                } else {
                    //没有记录则将学校表里的这一排名列置为null
                    school.setSchool_tws(null);
                    schoolService.updateById(school);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---Twscode不存在");
            return ResponseResult.FAILED("Twscode不存在");
        }
    }

    @GetMapping("/tws/getAllYears")
    public ResponseResult getAllTwsYears() {
        QueryWrapper<RankTws> qw = new QueryWrapper<>();
        qw.select("distinct tws_year");
        List<Object> years = twsService.listObjs(qw);
        log.info("---查询qs排名所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }

    @PostMapping("/us/add")
    public ResponseResult addUsRank(@RequestBody RankUs addUs) {
        //判断添加的us排名已存在
        QueryWrapper<RankUs> qw = new QueryWrapper<>();
        qw.eq("us_school", addUs.getUs_school()).eq("us_year", addUs.getUs_year());
        RankUs us = usService.getOne(qw);
        if (us == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addUs.getUs_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = usService.save(addUs);
                if (result) {
                    log.info("---新增Us排名" + addUs);
                    // 根据是否最新修改school里面的最新排名字段
                    Integer schoolRank = school.getSchool_us();
                    if (schoolRank != null) {
                        //需要判断是否为最新排名
                        RankUs newest = usService.findNewest(school.getSchool_id());
                        if (Integer.parseInt(addUs.getUs_year()) == Integer.parseInt(newest.getUs_year())) {
                            //表明是最新的
                            school.setSchool_us(addUs.getUs_rank());
                            schoolService.updateById(school);
                        }
                    } else {
                        school.setSchool_us(addUs.getUs_rank());
                        schoolService.updateById(school);
                    }
                    return ResponseResult.SUCCESS().setData(addUs);
                } else {
                    log.info("---新增失败" + addUs);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addUs);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该Us排名已经存在此表中" + addUs);
            return ResponseResult.FAILED("该Us排名已经存在！请勿重复添加");
        }
    }

    @PostMapping("/us/list")
    public ResponseResult getAllUsRank(@RequestBody RankDTO rankDTO) {
        int currentPage = rankDTO.getCurrentPage();
        int pageSize = rankDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = rankDTO.getUs_year();
        Page pageUsRanks = usService.findAll(pg, year);
        log.info("---查询Us排名" + pageUsRanks);
        return ResponseResult.SUCCESS().setData(pageUsRanks);
    }

    @PostMapping("/us/listAll")
    public ResponseResult getAllUss() {
        List<RankUs> uss = usService.list();
        if (!uss.isEmpty()) {
            log.info(uss + "---查询所有Us排名");
            return ResponseResult.SUCCESS().setData(uss);
        } else {
            log.info("---Us排名表无数据");
            return ResponseResult.FAILED("---Us排名表无数据");
        }
    }

    @PostMapping("/us/update")
    public ResponseResult updateUsRank(@RequestBody RankUs updateUs) {
        boolean result = usService.updateById(updateUs);
        if (result) {
            log.info("---修改成功" + updateUs);
            return ResponseResult.SUCCESS().setData(updateUs);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/us/delete/{usCode}")
    public ResponseResult deleteUsRank(@PathVariable("usCode") long usCode) {
        // 根据code获取实例对象
        RankUs us = usService.getById(usCode);
        if (us != null) {
            boolean result = usService.removeById(usCode);
            if (result) {
                log.info("---删除Us排名信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", us.getUs_school());
                InfoSchool school = schoolService.getOne(qw);
                RankUs newest = usService.findNewest(school.getSchool_id());
                if (newest != null) {
                    //表明该学校还有排名记录
                    school.setSchool_us(newest.getUs_rank());
                    schoolService.updateById(school);
                } else {
                    //没有记录则将学校表里的这一排名列置为null
                    school.setSchool_us(null);
                    schoolService.updateById(school);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---Uscode不存在");
            return ResponseResult.FAILED("Uscode不存在");
        }
    }

    @GetMapping("/us/getAllYears")
    public ResponseResult getAllUsYears() {
        QueryWrapper<RankUs> qw = new QueryWrapper<>();
        qw.select("distinct us_year");
        List<Object> years = usService.listObjs(qw);
        log.info("---查询qs排名所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }

    @PostMapping("/wsl/add")
    public ResponseResult addWslRank(@RequestBody RankWsl addWsl) {
        //判断添加的wsl排名已存在
        QueryWrapper<RankWsl> qw = new QueryWrapper<>();
        qw.eq("wsl_school", addWsl.getWsl_school()).eq("wsl_year", addWsl.getWsl_year());
        RankWsl wsl = wslService.getOne(qw);
        if (wsl == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addWsl.getWsl_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = wslService.save(addWsl);
                if (result) {
                    log.info("---新增Wsl排名" + addWsl);
                    // 根据是否最新修改school里面的最新排名字段
                    Integer schoolRank = school.getSchool_wsl();
                    if (schoolRank != null) {
                        //需要判断是否为最新排名
                        RankWsl newest = wslService.findNewest(school.getSchool_id());
                        if (Integer.parseInt(addWsl.getWsl_year()) == Integer.parseInt(newest.getWsl_year())) {
                            //表明是最新的
                            school.setSchool_wsl(addWsl.getWsl_rank());
                            schoolService.updateById(school);
                        }
                    } else {
                        school.setSchool_wsl(addWsl.getWsl_rank());
                        schoolService.updateById(school);
                    }
                    return ResponseResult.SUCCESS().setData(addWsl);
                } else {
                    log.info("---新增失败" + addWsl);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addWsl);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该Wsl排名已经存在此表中" + addWsl);
            return ResponseResult.FAILED("该Wsl排名已经存在！请勿重复添加");
        }
    }

    @PostMapping("/wsl/list")
    public ResponseResult getAllWslRank(@RequestBody RankDTO rankDTO) {
        int currentPage = rankDTO.getCurrentPage();
        int pageSize = rankDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = rankDTO.getWsl_year();
        Page pageWslRanks = wslService.findAll(pg,year);
        log.info("---查询Wsl排名" + pageWslRanks);
        return ResponseResult.SUCCESS().setData(pageWslRanks);
    }

    @PostMapping("/wsl/listAll")
    public ResponseResult getAllWsls() {
        List<RankWsl> wsls = wslService.list();
        if (!wsls.isEmpty()) {
            log.info(wsls + "---查询所有Wsl排名");
            return ResponseResult.SUCCESS().setData(wsls);
        } else {
            log.info("---Wsl排名表无数据");
            return ResponseResult.FAILED("---Wsl排名表无数据");
        }
    }

    @PostMapping("/wsl/update")
    public ResponseResult updateWslRank(@RequestBody RankWsl updateWsl) {
        boolean result = wslService.updateById(updateWsl);
        if (result) {
            log.info("---修改成功" + updateWsl);
            return ResponseResult.SUCCESS().setData(updateWsl);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/wsl/delete/{wslCode}")
    public ResponseResult deleteWslRank(@PathVariable("wslCode") long wslCode) {
        // 根据code获取实例对象
        RankWsl wsl = wslService.getById(wslCode);
        if (wsl != null) {
            boolean result = wslService.removeById(wslCode);
            if (result) {
                log.info("---删除Wsl排名信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", wsl.getWsl_school());
                InfoSchool school = schoolService.getOne(qw);
                RankUs newest = usService.findNewest(school.getSchool_id());
                if (newest != null) {
                    //表明该学校还有排名记录
                    school.setSchool_us(newest.getUs_rank());
                    schoolService.updateById(school);
                } else {
                    //没有记录则将学校表里的这一排名列置为null
                    school.setSchool_us(null);
                    schoolService.updateById(school);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---Wslcode不存在");
            return ResponseResult.FAILED("Wslcode不存在");
        }
    }

    @GetMapping("/wsl/getAllYears")
    public ResponseResult getAllWslYears() {
        QueryWrapper<RankWsl> qw = new QueryWrapper<>();
        qw.select("distinct wsl_year");
        List<Object> years = wslService.listObjs(qw);
        log.info("---查询qs排名所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }

    @PostMapping("/xyh/add")
    public ResponseResult addXyhRank(@RequestBody RankXyh addXyh) {
        //判断添加的xyh排名已存在
        QueryWrapper<RankXyh> qw = new QueryWrapper<>();
        qw.eq("xyh_school", addXyh.getXyh_school()).eq("xyh_year", addXyh.getXyh_year());
        RankXyh xyh = xyhService.getOne(qw);
        if (xyh == null) {
            // 该表依赖学校信息表，这个学校必须在学校信息表中存在
            QueryWrapper<InfoSchool> qw2 = new QueryWrapper<>();
            qw2.eq("school_id", addXyh.getXyh_school());
            InfoSchool school = schoolService.getOne(qw2);
            if (school != null) {
                boolean result = xyhService.save(addXyh);
                if (result) {
                    log.info("---新增Xyh排名" + addXyh);
                    // 根据是否最新修改school里面的最新排名字段
                    Integer schoolRank = school.getSchool_xyh();
                    if (schoolRank != null) {
                        //需要判断是否为最新排名
                        RankXyh newest = xyhService.findNewest(school.getSchool_id());
                        if (Integer.parseInt(addXyh.getXyh_year()) == Integer.parseInt(newest.getXyh_year())) {
                            //表明是最新的
                            school.setSchool_xyh(addXyh.getXyh_rank());
                            schoolService.updateById(school);
                        }
                    } else {
                        school.setSchool_xyh(addXyh.getXyh_rank());
                        schoolService.updateById(school);
                    }
                    return ResponseResult.SUCCESS().setData(addXyh);
                } else {
                    log.info("---新增失败" + addXyh);
                    return ResponseResult.FAILED("新增失败");
                }
            } else {
                log.info("---学校信息表中无此学校" + addXyh);
                return ResponseResult.FAILED("请先添加该学校基本信息，再进行操作");
            }
        } else {
            log.info("---该Xyh排名已经存在此表中" + addXyh);
            return ResponseResult.FAILED("该Xyh排名已经存在！请勿重复添加");
        }
    }

    @PostMapping("/xyh/list")
    public ResponseResult getAllXyhRank(@RequestBody RankDTO rankDTO) {
        int currentPage = rankDTO.getCurrentPage();
        int pageSize = rankDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String year = rankDTO.getXyh_year();
        Page pageXyhRanks = xyhService.findAll(pg,year);
        log.info("---查询Xyh排名" + pageXyhRanks);
        return ResponseResult.SUCCESS().setData(pageXyhRanks);
    }

    @PostMapping("/xyh/listAll")
    public ResponseResult getAllXyhs() {
        List<RankXyh> xyhs = xyhService.list();
        if (!xyhs.isEmpty()) {
            log.info(xyhs + "---查询所有Xyh排名");
            return ResponseResult.SUCCESS().setData(xyhs);
        } else {
            log.info("---Xyh排名表无数据");
            return ResponseResult.FAILED("---Xyh排名表无数据");
        }
    }

    @PostMapping("/xyh/update")
    public ResponseResult updateXyhRank(@RequestBody RankXyh updateXyh) {
        boolean result = xyhService.updateById(updateXyh);
        if (result) {
            log.info("---修改成功" + updateXyh);
            return ResponseResult.SUCCESS().setData(updateXyh);
        } else {
            log.info("---修改失败");
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/xyh/delete/{xyhCode}")
    public ResponseResult deleteXyhRank(@PathVariable("xyhCode") long xyhCode) {
        // 根据code获取实例对象
        RankXyh xyh = xyhService.getById(xyhCode);
        if (xyh != null) {
            boolean result = xyhService.removeById(xyhCode);
            if (result) {
                log.info("---删除Xyh排名信息表记录成功");
                // 查询依赖的学校信息
                QueryWrapper<InfoSchool> qw = new QueryWrapper<>();
                qw.eq("school_id", xyh.getXyh_school());
                InfoSchool school = schoolService.getOne(qw);
                RankXyh newest = xyhService.findNewest(school.getSchool_id());
                if (newest != null) {
                    //表明该学校还有排名记录
                    school.setSchool_xyh(newest.getXyh_rank());
                    schoolService.updateById(school);
                } else {
                    //没有记录则将学校表里的这一排名列置为null
                    school.setSchool_xyh(null);
                    schoolService.updateById(school);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败");
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---Xyhcode不存在");
            return ResponseResult.FAILED("Xyhcode不存在");
        }
    }

    @GetMapping("/xyh/getAllYears")
    public ResponseResult getAllXyhYears() {
        QueryWrapper<RankXyh> qw = new QueryWrapper<>();
        qw.select("distinct xyh_year");
        List<Object> years = xyhService.listObjs(qw);
        log.info("---查询qs排名所有年份" + years);
        return ResponseResult.SUCCESS().setData(years);
    }
}
