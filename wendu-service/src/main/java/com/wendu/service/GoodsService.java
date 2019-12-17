package com.wendu.service;

import com.wendu.entily.PageResult;
import com.wendu.model.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsService {

    /**
     * 成本修改 根据id和成本价格修改成本
     */
    void updateCost(Integer[] ids ,String cost);

    /**
     * 显示输入与未输入
     */
    List<Goods>  findActualPrice(Map<String, Object> searchMap);
    /**
     * 显示输入与未输入+分页
     */
    PageResult<Goods> findActualPrice(Map<String, Object> searchMap,int page ,int size);







    /**
     * 查询全部数据
     */
    List<Goods> findAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Goods findById(Integer id);

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    List<Goods> findList(Map<String, Object> searchMap);

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    PageResult<Goods> findPage(int page, int size) ;

    /**
     * 分页+条件
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    PageResult<Goods> findPage(Map<String, Object> searchMap,int page, int size) ;

    /**
     * 添加信息
     * @param goods
     */
    void add(Goods goods);

    /**
     * 修改信息
     * @param goods
     */
    void update(Goods goods);

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
