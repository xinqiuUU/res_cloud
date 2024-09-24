package com.yc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.dao.ResfoodMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  //开启事务
@Slf4j
public class ResfoodBizImpl implements ResfoodBiz {

    @Autowired
    private ResfoodMapper resfoodDao;

    @Value("${spring.cache.cacheName}")
    private String cacheName;   // 缓存名称

    @Cacheable(cacheNames = "#{@resfoodBizImpl.cacheName}", key = "'all'")
    @Override
    public List<Resfood> findAll() {
        QueryWrapper<Resfood> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("fid");
        List<Resfood> list = resfoodDao.selectList(wrapper);
        return list;
    }

    @Cacheable(cacheNames = "#{@resfoodBizImpl.cacheName}", key = "#id")
    @Override
    public Resfood findById(Integer id) {
        return this.resfoodDao.selectById(id);
    }

    // 分页查询公共部分
    private Page<Resfood> findFoods(QueryWrapper<Resfood> wrapper, int pageno, int pageSize, String sortby, String sort) {
        if ("asc".equalsIgnoreCase(sort)) {
            wrapper.orderByAsc(sortby);
        } else {
            wrapper.orderByDesc(sortby);
        }
        Page<Resfood> page = new Page<>(pageno, pageSize);
        Page<Resfood> pageList = resfoodDao.selectPage(page, wrapper);
        log.info("总记录数:" + pageList.getTotal());
        log.info("当前页:" + pageList.getCurrent());
        log.info("每页记录数:" + pageList.getSize());
        return pageList;
    }

    @Override
    public Page<Resfood> findFoods(Resfood food, int pageno, int pageSize, String sortby, String sort) {
        QueryWrapper<Resfood> wrapper = new QueryWrapper<>();
        if (food != null) {
            if (food.getFname() != null && !"".equals(food.getFname())) {
                wrapper.like("fname", food.getFname());
            }
            if (food.getDetail() != null && !"".equals(food.getDetail())) {
                wrapper.like("detail", food.getDetail());
            }
        }
        return findFoods(wrapper, pageno, pageSize, sortby, sort);
    }

    @Cacheable(cacheNames = "#{@resfoodBizImpl.cacheName}", key = "#pageno + '-' + #pageSize + '-' + #sortby + '-' + #sort")
    @Override
    public Page<Resfood> findByPage(int pageno, int pageSize, String sortby, String sort) {
        QueryWrapper<Resfood> wrapper = new QueryWrapper<>();
        return this.findFoods(wrapper, pageno, pageSize, sortby, sort);
    }

    @CacheEvict(cacheNames = "#{@resfoodBizImpl.cacheName}", allEntries = true)
    @Transactional
    @Override
    public Integer addResfood(Resfood resfood) {
        this.resfoodDao.insert(resfood);
        return resfood.getFid();
    }
}

