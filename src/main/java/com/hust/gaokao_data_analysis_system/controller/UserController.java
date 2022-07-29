package com.hust.gaokao_data_analysis_system.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hust.gaokao_data_analysis_system.common.LoginUser;
import com.hust.gaokao_data_analysis_system.common.PageRequest;
import com.hust.gaokao_data_analysis_system.common.ResponseResult;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoUser;
import com.hust.gaokao_data_analysis_system.service.impl.InfoUserServiceImpl;
import com.hust.gaokao_data_analysis_system.utils.JwtUtil;
import com.hust.gaokao_data_analysis_system.utils.RedisUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Log4j
public class UserController {

    private InfoUserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private RedisUtil redisUtil;

    @Autowired
    public void setUserService(InfoUserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @PostMapping("/list")
    public ResponseResult getAllUser(@RequestBody PageRequest pageRequest) {
        int currentPage = pageRequest.getCurrentPage();
        int pageSize = pageRequest.getPageSize();
        Page pg = new Page<>(currentPage, pageSize);
        Page pageUsers = userService.page(pg);
        log.info("---分页查询所有用户" + pageUsers.getRecords());
        return ResponseResult.SUCCESS().setData(pageUsers);
    }
    @GetMapping("/{uid}")
    public ResponseResult getOneUser(@PathVariable("uid") long uid) {
        InfoUser user = userService.getById(uid);
        if (user != null) {
            log.info("---根据id查询用户" + user);
            return ResponseResult.SUCCESS().setData(user);
        } else {
            log.info("uid:" + uid + "---不存在此用户");
            return ResponseResult.FAILED("不存在此用户");
        }
    }

    //    @PostMapping("/login")
//    public ResponseResult userLogin(@RequestBody InfoUser loginUser, HttpSession session) {
//        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
//        qw.eq("username", loginUser.getUsername());
//        InfoUser user = userService.getOne(qw);
//        if (user != null) {
//            if (user.getPassword().equals(loginUser.getPassword())) {
//                // 存入session
//                session.setAttribute("user", user);
//                log.info(user + "---登录成功");
//                return ResponseResult.SUCCESS();
//            } else {
//                log.info(user + "---密码错误");
//                return ResponseResult.FAILED("密码错误");
//            }
//        } else {
//            log.info("---用户名错误");
//            return ResponseResult.FAILED("用户名错误");
//        }
//    }
    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody InfoUser loginUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            log.info("---密码错误" + loginUser);
            throw new InternalAuthenticationServiceException("密码错误，请重新检查");
        } else {
            //使用userid生成token
            LoginUser user = (LoginUser) authenticate.getPrincipal();
            String userid = String.valueOf(user.getUser().getUid());
            String jwt = JwtUtil.createJWT(userid);
            //authenticate存入redis,设置1小时过期
            redisUtil.setCacheObject("login:" + userid, user, 1, TimeUnit.HOURS);
            //把token响应给前端
            HashMap<String, String> token = new HashMap<>();
            token.put("token", jwt);
            log.info("---登录成功" + user);
            log.info("token:"+jwt);
            Map<String,Object> userAndToken = new HashMap<>();
            userAndToken.put("uid",user.getUser().getUid());
            userAndToken.put("username",user.getUsername());
            userAndToken.put("role",user.getPermissions());
            userAndToken.put("token",jwt);
            return ResponseResult.SUCCESS().setData(userAndToken);
        }
    }
    @GetMapping("/logout")
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userid = loginUser.getUser().getUid();
        redisUtil.deleteObject("login:" + userid);
        log.info("---注销成功" + loginUser.getUser());
        return ResponseResult.SUCCESS();
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
            // 密码加密
            registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
            boolean result = userService.save(registerUser);
            if (result) {
                log.info("---注册成功" + registerUser);
                return ResponseResult.SUCCESS().setData(registerUser);
            } else {
                log.info("---注册失败" + registerUser);
                return ResponseResult.FAILED("注册失败");
            }
        } else {
            log.info("---用户名已存在" + registerUser);
            return ResponseResult.FAILED("用户名已存在");
        }
    }
    @PostMapping("/update")
    public ResponseResult userUpdate(@RequestBody InfoUser updateUser) {
        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
        qw.eq("username", updateUser.getUsername());
        InfoUser user = userService.getOne(qw);
        // 校验密码是否更改
        String updatePassword = updateUser.getPassword();
        if (!passwordEncoder.matches(updatePassword, user.getPassword())) {
            // 加密
            updateUser.setPassword(passwordEncoder.encode(updatePassword));
            boolean result = userService.updateById(updateUser);
            if (result) {
                log.info("---修改成功" + updateUser);
                return ResponseResult.SUCCESS().setData(updateUser);
            } else {
                log.info("---修改失败" + updateUser);
                return ResponseResult.FAILED("修改失败");
            }
        } else {
            log.info("---修改失败,密码未更改" + updateUser);
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
