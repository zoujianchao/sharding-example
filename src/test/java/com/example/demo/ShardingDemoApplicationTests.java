package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Course;
import com.example.demo.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShardingDemoApplicationTests {

    @Resource
    private CourseMapper courseMapper;

    @Test
    public void addCourse() {
        for (int i = 0; i < 10; i++) {
            Course c = new Course();
//            c.setCid(Long.valueOf(i));
            c.setCname("shardingsphere");
            c.setUserId(Long.valueOf(1000 + i));
            c.setCstatus("1");
            courseMapper.insert(c);
        }
    }

    @Test
    public void queryCourse() {
        List<Course> courses = courseMapper.selectList(null);
        courses.forEach(System.out::println);
    }

    @Test
    public void queryCourseByCid() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
//        wrapper.eq("cid", 609815069894643712L);
        wrapper.in("cid", 609815070360211456L, 609815070414737409L);
        List<Course> courses = courseMapper.selectList(wrapper);
        courses.forEach(System.out::println);

    }

    @Test
    public void queryCourseByRang() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.between("cid", 609815070360211456L, 609815070414737409L);
        List<Course> courses = courseMapper.selectList(wrapper);
        courses.forEach(System.out::println);

    }

}
