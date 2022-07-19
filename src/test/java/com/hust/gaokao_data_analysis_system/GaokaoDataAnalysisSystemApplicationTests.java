package com.hust.gaokao_data_analysis_system;

import com.hust.gaokao_data_analysis_system.mapper.InfoUserMapper;
import com.hust.gaokao_data_analysis_system.pojo.entity.InfoUser;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Log4j
@SpringBootTest
class GaokaoDataAnalysisSystemApplicationTests {
    @Autowired
    private InfoUserMapper infoUserMapper;
    @Test
    void contextLoads() {
        InfoUser user = new InfoUser();
        user.setUsername("张三");
        user.setPassword("123");
        user.setRole("ROLE_USER");
        int result = infoUserMapper.insert(user);
        System.out.println(result);
    }

}
