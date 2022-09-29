package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.dto.SchoolMajorDTO;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoMajor;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajor;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolMajorMajor;
import com.hust.gaokao_data_analysis_system.pojo.entity.SchoolSubject;
import com.hust.gaokao_data_analysis_system.pojo.vo.MajorVo;
import com.hust.gaokao_data_analysis_system.pojo.vo.SchoolMajorVo;
import com.hust.gaokao_data_analysis_system.service.impl.InfoMajorServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolMajorMajorServiceImpl;
import com.hust.gaokao_data_analysis_system.service.impl.SchoolMajorServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schoolMajor")
@Log4j
public class SchoolMajorController {
    private SchoolMajorServiceImpl schoolMajorService;
    private SchoolMajorMajorServiceImpl schoolMajorMajorService;
    @Autowired
    public void setSchoolMajorService(SchoolMajorServiceImpl schoolMajorService) {
        this.schoolMajorService = schoolMajorService;
    }
    @Autowired
    public void setInfoMajorService(SchoolMajorMajorServiceImpl schoolMajorMajorService){
        this.schoolMajorMajorService = schoolMajorMajorService;
    }
    @PostMapping("/list")
    public ResponseResult getAllSchoolMajorByPage(@RequestBody SchoolMajorDTO schoolMajorDTO) {
        int currentPage = schoolMajorDTO.getCurrentPage();
        int pageSize = schoolMajorDTO.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        // 根据schoolId筛选
        System.out.println("---查询条件"+schoolMajorDTO);
        Page<SchoolMajorVo> schoolMajorVoPage = schoolMajorService.findAllByPage(pg,schoolMajorDTO.getSchool_id());
        log.info("---分页查询所有学校专业" + schoolMajorVoPage.getRecords());
        return ResponseResult.SUCCESS().setData(schoolMajorVoPage);
    }

    @GetMapping("/listAll/{schoolId}")
    public ResponseResult getAllSchoolMajorBySchool(@PathVariable("schoolId") long schoolId) {
        List<SchoolMajorVo> schoolMajorVoList = schoolMajorService.findAll(schoolId);
        log.info("---查询该学校所有专业" + schoolMajorVoList);
        return ResponseResult.SUCCESS().setData(schoolMajorVoList);
    }

    @GetMapping("/listAllMajors/{schoolId}")
    public ResponseResult getSchoolMajorList(@PathVariable("schoolId") long schoolId){
        List<MajorVo> majorList = schoolMajorService.findAllMajorsBySchool(schoolId);
        log.info("---查询该学校专业的所有对应专业"+majorList);
        return ResponseResult.SUCCESS().setData(majorList);
    }

    @PostMapping("/add")
    public ResponseResult addSchoolMajor(@RequestBody SchoolMajor addSchoolMajor) {
        QueryWrapper<SchoolMajor> qw = new QueryWrapper<>();
        qw.eq("school_major_school", addSchoolMajor.getSchool_major_school());
        qw.eq("school_major_name", addSchoolMajor.getSchool_major_name());
        SchoolMajor schoolMajor = schoolMajorService.getOne(qw);
        if (schoolMajor == null) {
            boolean result = schoolMajorService.save(addSchoolMajor);
            if (result) {
                log.info("---新增学校专业" + addSchoolMajor);
                // 新增成功后将对应的专业列表添加到中间表中
                List<String> infoMajorList = addSchoolMajor.getMajorIdList();
                List<SchoolMajorMajor> schoolMajorMajorList = new ArrayList<>();
                for (String majorId: infoMajorList) {
                    SchoolMajorMajor schoolMajorMajor = new SchoolMajorMajor();
                    schoolMajorMajor.setSchoolMajor_major_schoolMajor(addSchoolMajor.getSchool_major_code());
                    schoolMajorMajor.setSchoolMajor_major_major(majorId);
                    schoolMajorMajorList.add(schoolMajorMajor);
                }
                boolean result2 = schoolMajorMajorService.saveBatch(schoolMajorMajorList);
                if (result2){
                    log.info("---新增学校专业-专业中间表记录"+schoolMajorMajorList);
                }
                else {
                    log.info("---新增学校专业-专业中间表记录失败"+schoolMajorMajorList);
                }
                return ResponseResult.SUCCESS();
            } else {
                log.info("---新增失败" + addSchoolMajor);
                return ResponseResult.FAILED("新增失败");
            }
        } else {
            log.info("---该学校已存在此专业");
            return ResponseResult.FAILED("该学校已存在此专业，请勿重复添加");
        }
    }

    @PostMapping("/update")
    public ResponseResult updateSchoolMajor(@RequestBody SchoolMajor updateSchoolMajor) {

        boolean result = schoolMajorService.updateById(updateSchoolMajor);
        if (result) {
            log.info("---修改成功" + updateSchoolMajor);
            // 先删除学校专业-专业中间表的相关记录
            QueryWrapper<SchoolMajorMajor> qw = new QueryWrapper<>();
            qw.eq("schoolMajor_major_schoolMajor",updateSchoolMajor.getSchool_major_code());
            boolean result2 = schoolMajorMajorService.remove(qw);
            if (result2){
                log.info("---删除删除学校专业-专业中间表的相关记录成功");
            }
            else {
                log.info("---删除学校专业-专业中间表的相关记录失败");
            }
            // 再添加修改后的记录
            List<String> infoMajorList = updateSchoolMajor.getMajorIdList();
            List<SchoolMajorMajor> schoolMajorMajorList = new ArrayList<>();
            for (String majorId: infoMajorList) {
                SchoolMajorMajor schoolMajorMajor = new SchoolMajorMajor();
                schoolMajorMajor.setSchoolMajor_major_schoolMajor(updateSchoolMajor.getSchool_major_code());
                schoolMajorMajor.setSchoolMajor_major_major(majorId);
                schoolMajorMajorList.add(schoolMajorMajor);
            }
            boolean result3 = schoolMajorMajorService.saveBatch(schoolMajorMajorList);
            if (result3){
                log.info("---新增学校专业-专业中间表记录"+schoolMajorMajorList);
            }
            else {
                log.info("---新增学校专业-专业中间表记录失败"+schoolMajorMajorList);
            }
            return ResponseResult.SUCCESS();
        } else {
            log.info("---修改失败" + updateSchoolMajor);
            return ResponseResult.FAILED("修改失败");
        }
    }

    @DeleteMapping("/delete/{schoolMajorCode}")
    public ResponseResult deleteSchoolMajor(@PathVariable("schoolMajorCode") long schoolMajorCode) {
        SchoolMajor schoolMajor = schoolMajorService.getById(schoolMajorCode);
        if (schoolMajor != null) {
            boolean result = schoolMajorService.removeById(schoolMajorCode);
            if (result) {
                log.info("---删除学校专业" + schoolMajor);
                return ResponseResult.SUCCESS();
            } else {
                log.info("---删除失败" + schoolMajor);
                return ResponseResult.FAILED("删除失败");
            }
        } else {
            log.info("---该学校专业不存在");
            return ResponseResult.FAILED("该学校专业不存在");
        }
    }
}
