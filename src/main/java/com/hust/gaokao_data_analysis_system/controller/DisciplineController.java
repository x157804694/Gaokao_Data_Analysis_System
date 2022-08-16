package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.DisciplineInfoDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoDiscipline;
import com.hust.gaokao_data_analysis_system.service.impl.InfoDisciplineServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/discipline")
@Log4j
public class DisciplineController {
    private InfoDisciplineServiceImpl disciplineService;

    @Autowired
    public void setDisciplineService(InfoDisciplineServiceImpl disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping("/list")
    public ResponseResult getAllDisciplineByPage(@RequestBody DisciplineInfoDTO disciplineInfoDTO) {
        int currentPage = disciplineInfoDTO.getCurrentPage();
        int pageSize = disciplineInfoDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        String discipline_level = disciplineInfoDTO.getDiscipline_level();
        Page pageDisciplines = null;
        QueryWrapper<InfoDiscipline> qw = new QueryWrapper<>();
        qw.orderByAsc("discipline_id");
        if (discipline_level!=null){
            qw.eq("discipline_level", discipline_level);
            pageDisciplines = disciplineService.page(pg,qw);
            log.info("---根据等级分页查询所有门类" + pageDisciplines.getRecords());
        }
        else {
            pageDisciplines = disciplineService.page(pg,qw);
            log.info("---分页查询所有门类" + pageDisciplines.getRecords());
        }
        return ResponseResult.SUCCESS().setData(pageDisciplines);
    }

    @GetMapping ("/listAll")
    public ResponseResult getAllDiscipline() {
        QueryWrapper<InfoDiscipline> qw = new QueryWrapper<>();
        List<String> orderlist = new ArrayList<>();
        orderlist.add("discipline_id");
        orderlist.add("discipline_level");
        qw.orderByAsc(orderlist);
        List<InfoDiscipline> disciplines = disciplineService.list(qw);
        log.info("---查询所有门类" + disciplines);
        return ResponseResult.SUCCESS().setData(disciplines);
    }
    @GetMapping("/listAll/{discipline_level}")
    public ResponseResult getAllDisciplineByLevel(@PathVariable("discipline_level") String discipline_level) {
        QueryWrapper<InfoDiscipline> qw = new QueryWrapper<>();
        qw.eq("discipline_level", discipline_level);
        qw.orderByAsc("discipline_id");
        List<InfoDiscipline> disciplines = disciplineService.list(qw);
        log.info("---根据门类等级查询相应门类" + disciplines);
        return ResponseResult.SUCCESS().setData(disciplines);
    }

    @PostMapping("/add")
    public ResponseResult addDiscipline(@RequestBody InfoDiscipline addDiscipline){
        // 判断是否存在同编号门类
        QueryWrapper<InfoDiscipline> qw = new QueryWrapper<>();
        InfoDiscipline discipline = disciplineService.getOne(qw.eq("discipline_id",addDiscipline.getDiscipline_id()));
        if (discipline==null){
            boolean result = disciplineService.save(addDiscipline);
            if (result){
                log.info("---新增学科门类"+addDiscipline);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---新增学科门类失败"+addDiscipline);
                return ResponseResult.FAILED("新增学科门类失败");
            }
        }
        else {
            log.info("---存在同编号学科门类"+addDiscipline);
            return ResponseResult.FAILED("学科门类id已存在，请勿重复添加！");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateDiscipline(@RequestBody InfoDiscipline updateDiscipline){
        boolean result = disciplineService.updateById(updateDiscipline);
        if (result){
            log.info("---修改学科门类成功"+updateDiscipline);
            return ResponseResult.SUCCESS();
        }
        else {
            log.info("---修改学科门类失败"+updateDiscipline);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{disciplineCode}")
    public ResponseResult deleteDiscipline(@PathVariable("disciplineCode") long disciplineCode){
        InfoDiscipline discipline = disciplineService.getById(disciplineCode);
        if (discipline!=null){
            boolean result = disciplineService.removeById(disciplineCode);
            if (result){
                log.info("---学科门类删除成功"+disciplineCode);
                return ResponseResult.SUCCESS();
            }
            else {
                log.info("---学科门类删除失败"+disciplineCode);
                return ResponseResult.FAILED("删除失败");
            }
        }
        else {
            log.info("---学科门类编号不存在");
            return ResponseResult.FAILED("学科门类编号不存在");
        }
    }

    @GetMapping("/getAllDisciplineLevel")
    public ResponseResult getAllDisciplineLevel(){
        QueryWrapper<InfoDiscipline> qw = new QueryWrapper<>();
        qw.select("distinct discipline_level");
        List<Object> levelList = disciplineService.listObjs(qw);
        log.info("---查询门类所有等级"+levelList);
        return ResponseResult.SUCCESS().setData(levelList);
    }
}
