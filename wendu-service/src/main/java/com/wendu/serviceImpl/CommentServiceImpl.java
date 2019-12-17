package com.wendu.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wendu.dao.CommentDao;
import com.wendu.entily.PageResult;
import com.wendu.model.Comment;
import com.wendu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;
    /**
     * 查询全部信息
     * @return
     */
    @Override
    public List<Comment> findAll() {
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete","1");
        return commentDao.selectByExample(example);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Comment findById(String id) {
        return commentDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Comment> findList(Map<String, Object> searchMap) {
        return null;
    }

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Comment> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Comment> comment = (Page<Comment>) commentDao.selectAll();
        return new PageResult(comment.getTotal(),comment.getResult());
    }

    /**
     * 条件+分页
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Comment> findPage(Map<String, Object> searchMap, int page, int size) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Comment comment) {
        commentDao.insert(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Comment comment) {
        commentDao.updateByPrimaryKeySelective(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(String id) {
        Comment Comment = commentDao.selectByPrimaryKey(id);
        if (Comment == null){
            throw new RuntimeException("当前用户不存在");
        }
        commentDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteById(String id) {
        Comment comment = commentDao.selectByPrimaryKey(id);
        if (comment == null){
            throw new RuntimeException("当前用户不存在");
        }
        comment.setIsDelete("0");
        commentDao.updateByPrimaryKeySelective(comment);

    }
}
