package com.example.demo;

import com.example.demo.entity.Course;
import com.example.demo.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShardingDemoApplicationTests {

    @Resource
    private CourseMapper courseMapper;

    @Test
    public void addCourse(){
        for (int i = 0; i < 10; i++) {
            Course c = new Course();
            c.setCid(Long.valueOf(i));
            c.setCname("shardingsphere");
            c.setUserId(Long.valueOf(1000 + i));
            c.setCstatus("1");
            courseMapper.insert(c);
        }
    }

}
