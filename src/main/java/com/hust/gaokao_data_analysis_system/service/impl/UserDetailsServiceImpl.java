package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hust.gaokao_data_analysis_system.common.LoginUser;
import com.hust.gaokao_data_analysis_system.mapper.InfoUserMapper;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private InfoUserMapper userMapper;

    @Autowired
    public void setUserMapper(InfoUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<InfoUser> qw = new LambdaQueryWrapper<>();
        qw.eq(InfoUser::getUsername,username);
        InfoUser user = userMapper.selectOne(qw);
        if (Objects.isNull(user)){
            throw new BadCredentialsException("用户名不存在,请重新检查");
        }
        // 查询对应的权限
        List<String> roles = Arrays.asList(user.getRole());
        return new LoginUser(user,roles);
    }
}
