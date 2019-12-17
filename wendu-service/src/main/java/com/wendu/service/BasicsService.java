package com.wendu.service;


import com.wendu.entily.PageResult;
import com.wendu.model.Basics;

import java.util.List;
import java.util.Map;

public interface BasicsService {
    /**
     * 查询全部数据
     */
    List<Basics> findAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Basics findById(Integer id);

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    List<Basics> findList(Map<String, Object> searchMap);

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    PageResult<Basics> findPage(int page, int size) ;

    /**
     * 分页+条件
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    PageResult<Basics> findPage(Map<String, Object> searchMap,int page, int size) ;

    /**
     * 添加信息
     * @param basics
     */
    void add(Basics basics);

    /**
     * 修改信息
     * @param basics
     */
    void update(Basics basics);

    /**
     * 根据id删除信息
     * @param id
     */
    void delete (Integer id);



    /**
     * 根据id删除
     * 逻辑删除
     * @param id
     */
    void deleteById(Integer id);


}
