package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;

import java.util.List;

public interface ResfoodBiz {

    //查询所有商品
    public List<Resfood> findAll();

    //根据id查询商品
    public Resfood findById(Integer id);


    /* 带条件的分页查询  */
    public Page<Resfood> findFoods(Resfood food, int pageno, int pageSize, String sortby, String sort);


    /* 带条件的分页查询  */
    public Page<Resfood> findByPage(int pageno, int pageSize, String sortby, String sort);

    //添加商品
    public Integer addResfood( Resfood resfood );


}
