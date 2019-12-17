package com.wendu.service;

import com.github.pagehelper.Page;
import com.wendu.entily.PageResult;
import com.wendu.model.Admin;

import java.util.List;
import java.util.Map;

public interface AdminService {
    /**
     * 查询全部信息
     * @return
     */
    List<Admin> findAll();
    /**
     *
     * @param id 用户id
     * @return 用户信息
     */
    Admin findById(Integer id);

    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    List<Admin> findList(Map searchMap);

    /**
     * 条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    PageResult<Admin> findPage(Map searchMap, int page, int size);

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    PageResult<Admin> findPage( int page, int size);

    Admin findByUsername(String name);

    /**
     * 添加用户
     * @param admin 用户信息
     */
    void add(Admin admin);
    /**
     * 修改用户
     * @param admin 用户信息
     */
    void update(Admin admin);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 根据id删除
     * 逻辑删除
     * @param id
     */
    void deleteById(String id);


}
