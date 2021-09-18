package com.ym.springbootproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class SpringbootProjectApplicationTests {


    @Test
    void contextLoads() {
    }

    @Autowired
    private RedisTemplate redisTemplate;


}
