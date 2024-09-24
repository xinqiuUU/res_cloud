package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.ResfoodApp;
import com.yc.bean.Resfood;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResfoodApp.class)
@Slf4j
class ResfoodBizImplTest {

    @Autowired
    private ResfoodBiz resfoodBiz;

    @Test
    void findAll() {
        List<Resfood> list = resfoodBiz.findAll();
        log.info(list.size()+"条数据");
        assertNotNull(list);
        List<Resfood> list1 = resfoodBiz.findAll();
        log.info(list1.size()+"条数据");
    }

    @Test
    void findById() {
        Resfood resfood = resfoodBiz.findById(1);
        log.info(resfood.toString());
        assertNotNull(resfood);
        Resfood resfood1 = resfoodBiz.findById(1);
        log.info(resfood1.toString());
    }

    @Test
    void findFoods() {
        Resfood resfood = new Resfood();
        resfood.setFname("鱼香肉丝");
        resfood.setDetail("6666");
        Page<Resfood> page = resfoodBiz.findFoods(resfood, 1, 10, "fid", "desc");
        log.info(page.getTotal()+"条数据");
        assertNotNull(page);

        Page<Resfood> page1 = resfoodBiz.findFoods(resfood, 1, 10, "fid", "desc");
        log.info(page1.getTotal()+"条数据");
    }

    @Test
    void findByPage() {
        Page<Resfood> page = resfoodBiz.findByPage(1, 5, "fid", "asc");
        log.info(page.getTotal()+"条数据");
        assertNotNull(page);
        Page<Resfood> page1 = resfoodBiz.findByPage(1, 5, "fid", "asc");
        log.info(page1.getTotal()+"条数据");
    }

    @Test
    void addResfood() {
        Resfood resfood = new Resfood();
        resfood.setFname("鱼香肉丝");
        resfood.setDetail("好吃好吃");
        resfood.setRealprice( 100.0);
        resfood.setNormprice( 25.0 );
        resfood.setFphoto("1.jpg");

        Integer fid = resfoodBiz.addResfood(resfood);
        assertNotNull(fid);
        log.info("新增菜品编号:"+fid);
    }
}