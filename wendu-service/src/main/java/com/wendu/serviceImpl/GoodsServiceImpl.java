package com.wendu.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wendu.dao.GoodsDao;
import com.wendu.entily.PageResult;
import com.wendu.model.Goods;
import com.wendu.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author LittleDumpling
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 成本修改
     * @param ids id
     * @param cost 成本价格
     */
    @Override
    @Transactional(readOnly = false)
    public void updateCost(Integer[] ids, String cost) {
        //查询出商品
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("id", Arrays.asList(ids));
        criteria.andEqualTo("isDelete","1");
        List<Goods> goodsList = goodsDao.selectByExample(criteria);

        if ( goodsList == null){throw new RuntimeException("参数有误");}
        //遍历修改
        for (Goods goods : goodsList) {
            goods.setGoodActualPrice(cost);
            goodsDao.updateByPrimaryKeySelective(goods);
        }

    }

    /**
     * 显示输入与未输入
     */
    @Override
   public List<Goods> findActualPrice(Map<String, Object> searchMap){
        Example exam = createExam(searchMap);
        return goodsDao.selectByExample(exam);
    }
    /**
     * 显示输入与未输入+分页
     */
    @Override
    public PageResult<Goods> findActualPrice(Map<String, Object> searchMap,int page ,int size){
        Example exam = createExam(searchMap);
        Page<Goods> goodsPage = (Page<Goods>) goodsDao.selectByExample(exam);
        return new PageResult<>(goodsPage.getTotal(),goodsPage.getResult());
    }


    /**
     * 查询全部信息
     * @return
     */
    @Override
    public List<Goods> findAll() {
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete","1");
        return goodsDao.selectByExample(example);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Goods findById(Integer id) {
        return goodsDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Goods> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return goodsDao.selectByExample(example);
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Goods> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Goods> goods = (Page<Goods>) goodsDao.selectAll();
        return new PageResult(goods.getTotal(),goods.getResult());
    }

    /**
     * 条件+分页
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Goods> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Goods> goodList = (Page<Goods>) goodsDao.selectByExample(example);
        return new PageResult<>(goodList.getTotal(),goodList.getResult());
    }




    @Override
    @Transactional(readOnly = false)
    public void add(Goods goods) {
        goodsDao.insert(goods);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Goods goods) {
        goodsDao.updateByPrimaryKeySelective(goods);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Integer id) {
        Goods Goods = goodsDao.selectByPrimaryKey(id);
        if (Goods == null){
            throw new RuntimeException("当前用户不存在");
        }
        goodsDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        Goods goods = goodsDao.selectByPrimaryKey(id);
        if (goods == null){
            throw new RuntimeException("当前用户不存在");
        }
        goods.setIsDelete("0");
        goodsDao.updateByPrimaryKeySelective(goods);

    }


    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            if(searchMap.get("goodName")!=null && !"".equals(searchMap.get("goodName"))){
                criteria.andLike("goodName","%"+searchMap.get("goodName")+"%");
            }
        }
        return example;
    }
    private Example createExam(Map<String, Object> searchMap){
        Example example=new Example(Goods.class);


        if(searchMap!=null){
            //排序 按照单价排序
            if(searchMap.get("goodPrice") !=  null && !"".equals(searchMap.get("goodPrice"))){
                example.setOrderByClause("good_price "+" DESC");
            }

            Example.Criteria criteria = example.createCriteria();
            if(searchMap.get("goodName")!=null && !"".equals(searchMap.get("goodName"))){
                criteria.andLike("goodName","%"+searchMap.get("goodName")+"%");
            }
            //显示输入的
            if(searchMap.get("goodActualPrice")!=null && !"".equals(searchMap.get("goodActualPrice") ) ){
                criteria.andIsNotNull("goodActualPrice");
            }
            //显示显示未输入的
            if(searchMap.get("goodActualPrice")== null && "".equals(searchMap.get("goodActualPrice"))){
                criteria.andIsNull("goodActualPrice");
            }

        }
        return example;
    }
}
