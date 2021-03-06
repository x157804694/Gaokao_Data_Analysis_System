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
        log.info("---????????????????????????" + pageUsers.getRecords());
        return ResponseResult.SUCCESS().setData(pageUsers);
    }
    @GetMapping("/{uid}")
    public ResponseResult getOneUser(@PathVariable("uid") long uid) {
        InfoUser user = userService.getById(uid);
        if (user != null) {
            log.info("---??????id????????????" + user);
            return ResponseResult.SUCCESS().setData(user);
        } else {
            log.info("uid:" + uid + "---??????????????????");
            return ResponseResult.FAILED("??????????????????");
        }
    }

    //    @PostMapping("/login")
//    public ResponseResult userLogin(@RequestBody InfoUser loginUser, HttpSession session) {
//        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
//        qw.eq("username", loginUser.getUsername());
//        InfoUser user = userService.getOne(qw);
//        if (user != null) {
//            if (user.getPassword().equals(loginUser.getPassword())) {
//                // ??????session
//                session.setAttribute("user", user);
//                log.info(user + "---????????????");
//                return ResponseResult.SUCCESS();
//            } else {
//                log.info(user + "---????????????");
//                return ResponseResult.FAILED("????????????");
//            }
//        } else {
//            log.info("---???????????????");
//            return ResponseResult.FAILED("???????????????");
//        }
//    }
    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody InfoUser loginUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            log.info("---????????????" + loginUser);
            throw new InternalAuthenticationServiceException("??????????????????????????????");
        } else {
            //??????userid??????token
            LoginUser user = (LoginUser) authenticate.getPrincipal();
            String userid = String.valueOf(user.getUser().getUid());
            String jwt = JwtUtil.createJWT(userid);
            //authenticate??????redis,??????1????????????
            redisUtil.setCacheObject("login:" + userid, user, 1, TimeUnit.HOURS);
            //???token???????????????
            HashMap<String, String> token = new HashMap<>();
            token.put("token", jwt);
            log.info("---????????????" + user);
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
        log.info("---????????????" + loginUser.getUser());
        return ResponseResult.SUCCESS();
    }

    @PostMapping("/register")
    public ResponseResult userRegister(@RequestBody InfoUser registerUser) {
        // ??????????????????????????????
        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
        qw.eq("username", registerUser.getUsername());
        InfoUser user = userService.getOne(qw);
        if (user == null) {
            // ??????????????????
            registerUser.setRole("ROLE_USER");
            // ????????????
            registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
            boolean result = userService.save(registerUser);
            if (result) {
                log.info("---????????????" + registerUser);
                return ResponseResult.SUCCESS().setData(registerUser);
            } else {
                log.info("---????????????" + registerUser);
                return ResponseResult.FAILED("????????????");
            }
        } else {
            log.info("---??????????????????" + registerUser);
            return ResponseResult.FAILED("??????????????????");
        }
    }
    @PostMapping("/update")
    public ResponseResult userUpdate(@RequestBody InfoUser updateUser) {
        QueryWrapper<InfoUser> qw = new QueryWrapper<>();
        qw.eq("username", updateUser.getUsername());
        InfoUser user = userService.getOne(qw);
        // ????????????????????????
        String updatePassword = updateUser.getPassword();
        if (!passwordEncoder.matches(updatePassword, user.getPassword())) {
            // ??????
            updateUser.setPassword(passwordEncoder.encode(updatePassword));
            boolean result = userService.updateById(updateUser);
            if (result) {
                log.info("---????????????" + updateUser);
                return ResponseResult.SUCCESS().setData(updateUser);
            } else {
                log.info("---????????????" + updateUser);
                return ResponseResult.FAILED("????????????");
            }
        } else {
            log.info("---????????????,???????????????" + updateUser);
            return ResponseResult.FAILED("???????????????????????????????????????");
        }
    }
    @DeleteMapping("/delete/{uid}")
    public ResponseResult userDelete(@PathVariable("uid") long uid) {
        boolean result = userService.removeById(uid);
        if (result) {
            log.info("??????id???" + uid + "---?????????");
            return ResponseResult.SUCCESS();
        } else {
            log.info("??????id???" + uid + "---????????????");
            return ResponseResult.FAILED("????????????");
        }
    }
}
