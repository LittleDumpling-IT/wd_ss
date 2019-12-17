package com.wendu.service;

import com.wendu.entily.PageResult;
import com.wendu.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 查询全部数据
     */
    List<Comment> findAll();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Comment findById(String id);

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    List<Comment> findList(Map<String, Object> searchMap);

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    PageResult<Comment> findPage(int page, int size) ;

    /**
     * 分页+条件
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    PageResult<Comment> findPage(Map<String, Object> searchMap,int page, int size) ;

    /**
     * 添加信息
     * @param comment
     */
    void add(Comment comment);

    /**
     * 修改信息
     * @param comment
     */
    void update(Comment comment);

    /**
     * 根据id删除信息
     * @param id
     */
    void delete (String id);



    /**
     * 根据id删除
     * 逻辑删除
     * @param id
     */
    void deleteById(String id);
}
