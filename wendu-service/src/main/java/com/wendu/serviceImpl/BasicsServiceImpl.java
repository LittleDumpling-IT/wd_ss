package com.wendu.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wendu.dao.BasicsDao;
import com.wendu.entily.PageResult;
import com.wendu.model.Basics;
import com.wendu.service.BasicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class BasicsServiceImpl  implements BasicsService {

    @Autowired
    private BasicsDao basicsDao;
    /**
     * 查询全部信息
     * @return
     */
    @Override
    public List<Basics> findAll() {
        Example example = new Example(Basics.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete","1");
        return basicsDao.selectByExample(example);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Basics findById(Integer id) {
        return basicsDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Basics> findList(Map<String, Object> searchMap) {
        return null;
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Basics> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Basics> basics = (Page<Basics>) basicsDao.selectAll();
        return new PageResult(basics.getTotal(),basics.getResult());
    }

    /**
     * 条件+分页
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Basics> findPage(Map<String, Object> searchMap, int page, int size) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Basics basics) {
        basicsDao.insert(basics);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Basics basics) {
        basicsDao.updateByPrimaryKeySelective(basics);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Integer id) {
        Basics basics = basicsDao.selectByPrimaryKey(id);
        if (basics == null){
            throw new RuntimeException("当前用户不存在");
        }
        basicsDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        Basics basics = basicsDao.selectByPrimaryKey(id);
        if (basics == null){
            throw new RuntimeException("当前用户不存在");
        }
        basics.setIsDelete("0");
        basicsDao.updateByPrimaryKeySelective(basics);

    }
}
