package com.wendu.controller.goods;

import com.wendu.entily.Result;
import com.wendu.entily.StatusCode;
import com.wendu.model.Comment;
import com.wendu.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comment")
@Api(value = "评论管理",description = "评论管理")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/findAll")
    @ApiOperation(value = "查询全部数据",notes = "查询全部数据")
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",commentService.findAll());
    }

    @GetMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询数据",notes = "根据id查询数据")
    public Result findById(@PathVariable("id")String id){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findById(id));
    }


    @PostMapping("/search/{page}/{size}")
    @ApiOperation(value = "根据条件分页",notes = "根据条件分页")
    public Result findPage(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
        return  new Result(true, StatusCode.OK,"查询成功", commentService.findPage(searchMap, page, size)  );
    }
    @GetMapping("/search/{page}/{size}")
    @ApiOperation(value = "分页",notes = "分页")
    public Result findPage(@PathVariable int page, @PathVariable int size){
        return  new Result(true,StatusCode.OK,"查询成功", commentService.findPage(page, size) );
    }


    @PostMapping("/search")
    @ApiOperation(value = "根据条件查询",notes = "根据条件查询")
    public Result findList( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findList(searchMap));
    }

    @ApiOperation(value = "根据id删除用户",notes = "根据id删除用户")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id ){
        commentService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    @ApiOperation(value = "根据id逻辑删除用户",notes = "根据id逻辑删除用户")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id ){
        commentService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @ApiOperation(value = "添加基础数据",notes = "添加基础数据")
    @PostMapping("/save")
    public  Result save(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    @ApiOperation(value = "修改基础数据",notes = "修改基础数据")
    @PutMapping("/update")
    public Result update(@RequestBody Comment comment){
        commentService.update(comment);
        return new Result(true,StatusCode.OK,"添加成功");

    }



}
