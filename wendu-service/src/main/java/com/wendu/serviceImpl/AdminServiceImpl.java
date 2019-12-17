package com.wendu.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wendu.dao.AdminDao;
import com.wendu.entily.PageResult;
import com.wendu.model.Admin;
import com.wendu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;


import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public List<Admin> findAll() {
        return adminDao.selectAll();
    }

    /**
     * 查询用户信息
     * @param id 用户id
     * @return
     */
    @Override
    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Admin> findList(Map searchMap) {
        Example example = createExample(searchMap);
        return adminDao.selectByExample(example);
    }

    @Override
    public PageResult<Admin> findPage(Map searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Admin> admins = (Page<Admin>) adminDao.selectByExample(example);
        return new PageResult<Admin>(admins.getTotal(),admins.getResult());
    }

    @Override
    public PageResult<Admin> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Admin> admins = (Page<Admin>) adminDao.selectAll();
        return new PageResult<Admin>(admins.getTotal(),admins.getResult());
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */

    @Override
    public Admin findByUsername(String username) {
        if (username != null &&"".equals(username)){
            throw  new RuntimeException("请输入正确的参数");
        }
        return adminDao.selectByPrimaryKey(username);
    }

    /**
     *
     * @param admin 用户信息
     */
    @Override
    @Transactional(readOnly = false)
    public void add(Admin admin) {
        //根据用户名或者手机号查询用户
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        if (null == admin ){
            throw  new RuntimeException("");
        }
        if (admin.getMobile()!= null && "".equals(admin.getMobile())){
            criteria.andEqualTo("mobile",admin.getMobile());
        }
        if (admin.getUsername() != null &&"".equals(admin.getUsername())){
            criteria.andEqualTo("username",admin.getUsername());
        }

        Admin selectAdmin = adminDao.selectOneByExample(example);
        //判断用户是否存在
        if (selectAdmin != null){
            throw new RuntimeException("该用户已存在");
        }
        //添加用户
        adminDao.insert(admin);

    }

    /**
     * 修改用户信息
     * @param admin 用户信息
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Admin admin) {
        Admin selectAdmin = adminDao.selectByPrimaryKey(admin.getId());
        if (selectAdmin == null){
            throw new RuntimeException("当前用户不存在");
        }
        adminDao.updateByPrimaryKeySelective(admin);

    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id) {
        Admin admin = adminDao.selectByPrimaryKey(id);
        if (admin == null){
            throw new RuntimeException("当前用户不存在");
        }
        adminDao.deleteByPrimaryKey(id);
    }

    /**
     * 逻辑删除用户
     * @param id
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(String id) {
        Admin admin = adminDao.selectByPrimaryKey(id);
        if (admin == null){
            throw new RuntimeException("当前用户不存在");
        }
        admin.setIsDelete("0");
        adminDao.updateByPrimaryKeySelective(admin);
    }

    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // 用户名
            if(searchMap.get("username")!=null && !"".equals(searchMap.get("username"))){
                criteria.andLike("username","%"+searchMap.get("username")+"%");
            }
            // 密码，加密存储
            if(searchMap.get("password")!=null && !"".equals(searchMap.get("password"))){
                criteria.andLike("password","%"+searchMap.get("password")+"%");
            }
            // 注册手机号
            if(searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))){
                criteria.andLike("mobile","%"+searchMap.get("mobile")+"%");
            }


        }
        return example;
    }




}
