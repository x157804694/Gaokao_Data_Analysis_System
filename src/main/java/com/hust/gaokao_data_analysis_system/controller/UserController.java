package com.hust.gaokao_data_analysis_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoUser;
import com.hust.gaokao_data_analysis_system.service.impl.InfoUserServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Log4j
public class UserController {

    private InfoUserServiceImpl userService;

    @Autowired
    public void setUserService(InfoUserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/list")
    public ResponseResult getAllUser(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage,pageSize);
        Page pageUsers = userService.page(pg);
        log.info(pageUsers.getRecords()+"---分页查询所有用户");
        return ResponseResult.SUCCESS().setData(pageUsers);
    }

    @GetMapping ("/{uid}")
    public ResponseResult getOneUser(@PathVariable("uid") long uid){
        InfoUser user = userService.getById(uid);
        if (user!=null){
            log.info(user+"---根据id查询用户");
            return ResponseResult.SUCCESS().setData(user);
        }
        else {
            log.info("uid:"+ uid +"---不存在此用户");
            return ResponseResult.FAILED("不存在此用户");
        }
    }

    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody InfoUser loginUser, HttpSession session) {
        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
        qw.eq("username", loginUser.getUsername());
        InfoUser user = userService.getOne(qw);
        if (user != null) {
            if (user.getPassword().equals(loginUser.getPassword())) {
                // 存入session
                session.setAttribute("user", user);
                log.info(user + "---登录成功");
                return ResponseResult.SUCCESS();
            } else {
                log.info(user + "---密码错误");
                return ResponseResult.FAILED("密码错误");
            }
        } else {
            log.info("---用户名错误");
            return ResponseResult.FAILED("用户名错误");
        }
    }

    @PostMapping("/register")
    public ResponseResult userRegister(@RequestBody InfoUser registerUser) {
        // 判断是否存在同名账户
        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
        qw.eq("username", registerUser.getUsername());
        InfoUser user = userService.getOne(qw);
        if (user == null) {
            // 设置非管理员
            registerUser.setRole("ROLE_USER");
            boolean result = userService.save(registerUser);
            if (result) {
                log.info(registerUser + "---注册成功");
                return ResponseResult.SUCCESS().setData(registerUser);
            } else {
                log.info(registerUser + "---注册失败");
                return ResponseResult.FAILED("注册失败");
            }
        } else {
            log.info(registerUser + "---用户名已存在");
            return ResponseResult.FAILED("用户名已存在");
        }
    }

    @PostMapping("/update")
    public ResponseResult userUpdate(@RequestBody InfoUser updateUser) {
        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
        qw.eq("username", updateUser.getUsername());
        InfoUser user = userService.getOne(qw);
        // 校验密码是否更改
        if (!updateUser.getPassword().equals(user.getPassword())) {
            boolean result = userService.updateById(updateUser);
            if (result) {
                log.info(updateUser + "---修改成功");
                return ResponseResult.SUCCESS().setData(updateUser);
            } else {
                log.info(updateUser + "---修改失败");
                return ResponseResult.FAILED("修改失败");
            }
        } else {
            log.info(updateUser + "---修改失败,密码未更改");
            return ResponseResult.FAILED("密码未更改，请重新设置密码");
        }
    }

    @DeleteMapping("/delete/{uid}")
    public ResponseResult userDelete(@PathVariable("uid") long uid) {
        boolean result = userService.removeById(uid);
        if (result) {
            log.info("用户id：" + uid + "---被删除");
            return ResponseResult.SUCCESS();
        } else {
            log.info("用户id：" + uid + "---删除失败");
            return ResponseResult.FAILED("删除失败");
        }
    }
}
