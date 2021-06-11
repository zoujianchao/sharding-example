package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.Course;
import com.example.demo.entity.Dict;
import com.example.demo.entity.User;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.DictMapper;
import com.example.demo.mapper.UserMapper;
import net.minidev.json.writer.UpdaterMapper;
import org.apache.shardingsphere.api.hint.HintManager;
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
    @Resource
    private DictMapper dictMapper;
    @Resource
    private UserMapper userMapper;

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
        wrapper.in("cid", 609815070360211456L, 609815070414737409L, 132453465546534L);
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

    @Test
    public void queryCourseOrder() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("user_id"); //可以
        wrapper.eq("user_id", "1002"); //可以,全表查
        List<Course> courses = courseMapper.selectList(wrapper);
        courses.forEach(System.out::println);
    }

    @Test
    public void queryCourseHint() {
        HintManager hintManager = HintManager.getInstance();
        hintManager.addTableShardingValue("course", 2);
        List<Course> courses = courseMapper.selectList(null);
        courses.forEach(System.out::println);
        hintManager.close();
    }

    @Test
    public void addDict() {
        Dict d1 = new Dict();
//        d1.setDictId(Long.valueOf("1"));
        d1.setUstatus("1");
        d1.setUvalue("正常");
        dictMapper.insert(d1);

        Dict d2 = new Dict();
//        d2.setDictId(Long.valueOf("2"));
        d2.setUstatus("0");
        d2.setUvalue("不正常");
        dictMapper.insert(d2);

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("User No" + i);
            user.setUstatus("" + (i % 2));
            user.setUage(i * 10);
            userMapper.insert(user);
        }
    }

    @Test
    public void queryUserStatus() {
        List<User> users = userMapper.queryUserStatus();
        users.forEach(System.out::println);
    }


    //主从
    //主写
    @Test
    public void masterSlaveDict() {
        for (int i = 0; i < 10; i++) {
            Dict d = new Dict();
            d.setUstatus("" + (i % 2));
            d.setUvalue("Normal");
            dictMapper.insert(d);
        }
    }

    //从读
    @Test
    public void masterSlaveRead() {
        List<Dict> dicts = dictMapper.selectList(null);
        dicts.forEach(System.out::println);
    }

    //主写
    @Test
    public void masterSlaveUpdate() {
        Dict dict = new Dict();
        dict.setUstatus("0");
        dict.setUvalue("unNormal");

        UpdateWrapper<Dict> wrapper = new UpdateWrapper<>();
        wrapper.eq("ustatus", dict.getUstatus());
        dictMapper.update(dict, wrapper);
    }

}
