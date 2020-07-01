package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mp.entity.User;
import com.mp.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MPApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {

        // UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        // 所以不填写就是无任何条件
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert() {

        User user = new User();
        user.setName("Jerry");
        user.setAge(18);
        user.setEmail("55317332@qq.com");
        int result = userMapper.insert(user);
        System.out.println(result); // 影响的行数
        System.out.println(user); // id 自动回填
    }

    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(6L);
        user.setAge(38);
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    @Test
    public void testOptimisticLocker() {

        // 查询
        User user = userMapper.selectById(8L);
        // 修改数据
        user.setName("Helen Yao");
        user.setEmail("helen@qq.com");
        // 执行更新
        userMapper.updateById(user);
    }

    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(2L);
        idList.add(3L);
        idList.add(4L);
        List<User> users = userMapper.selectBatchIds(idList);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);

        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByWrapper() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age", 20);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectPage() {

        Page<User> page = new Page<>(2, 2);
        userMapper.selectPage(page, null);

        List<User> records = page.getRecords();
        records.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("总页数：" + page.getPages());
        System.out.println("每页显示记录数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("是否有下一页：" + page.hasNext());
        System.out.println("是否有上一页：" + page.hasPrevious());
    }

    @Test
    public void testSelectMapsPage() {
        Page<User> page = new Page<>(1, 5);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);
        // 注意：此行必须使用 mapIPage 获取记录列表，否则会有数据类型转换错误
        List<Map<String, Object>> records = mapIPage.getRecords();
        records.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("总页数：" + page.getPages());
        System.out.println("每页显示记录数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("是否有下一页：" + page.hasNext());
        System.out.println("是否有上一页：" + page.hasPrevious());
    }

    @Test
    public void testLogicDelete() {
        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }


}

