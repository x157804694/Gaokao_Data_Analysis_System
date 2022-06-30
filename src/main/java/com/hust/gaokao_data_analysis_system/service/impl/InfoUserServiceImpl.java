package com.hust.gaokao_data_analysis_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoUser;
import com.hust.gaokao_data_analysis_system.service.InfoUserService;
import com.hust.gaokao_data_analysis_system.mapper.InfoUserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XDL
* @description 针对表【t_info_user】的数据库操作Service实现
* @createDate 2022-06-27 11:16:06
*/
@Service
public class InfoUserServiceImpl extends ServiceImpl<InfoUserMapper, InfoUser> implements InfoUserService{
}




