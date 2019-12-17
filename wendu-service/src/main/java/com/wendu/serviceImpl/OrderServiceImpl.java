package com.wendu.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wendu.dao.OrderDao;
import com.wendu.entily.PageResult;
import com.wendu.model.Order;
import com.wendu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    /**
     * 查询全部信息
     * @return
     */
    @Override
    public List<Order> findAll() {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete","1");
        return orderDao.selectByExample(example);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Order findById(Integer id) {
        return orderDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> findList(Map<String, Object> searchMap) {
        return null;
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Order> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Order> order = (Page<Order>) orderDao.selectAll();
        return new PageResult(order.getTotal(),order.getResult());
    }

    /**
     * 条件+分页
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Order> findPage(Map<String, Object> searchMap, int page, int size) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Order order) {
        orderDao.insert(order);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Order order) {
        orderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Integer id) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order == null){
            throw new RuntimeException("当前用户不存在");
        }
        orderDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        Order order = orderDao.selectByPrimaryKey(id);
        if (order == null){
            throw new RuntimeException("当前用户不存在");
        }
        order.setIsDelete("0");
        orderDao.updateByPrimaryKeySelective(order);

    }
}
