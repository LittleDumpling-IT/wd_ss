package com.wendu.controller.goods;

import com.wendu.entily.PageResult;
import com.wendu.entily.Result;
import com.wendu.entily.StatusCode;
import com.wendu.model.Goods;
import com.wendu.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/goods")
@Api(value = "商品管理",description = "商品管理")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;



    /**
     * 成本修改 根据id和成本价格修改成本
     */
    @PostMapping("/updateCost")
    @ApiOperation(value = "输入成本修改",notes = "输入成本修改")
    public Result updateCost(Integer[] ids ,String cost){
        goodsService.updateCost(ids,cost);
        return new Result(true,StatusCode.OK,"设置成功");
    }

    /**
     * 多条件查询
     * 排序
     * 成本显示
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/findActualPrice/{page}/{size}")
    @ApiOperation(value = "多条件查询+分页",notes = "多条件查询+分页")
    public Result findActualPrice(@RequestBody Map<String, Object> searchMap,@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return new Result(true,StatusCode.OK,"查询成功",goodsService.findActualPrice(searchMap,page,size));
    }

    @PostMapping("/findActualPrice")
    @ApiOperation(value = "多条件查询",notes = "多条件查询")
    public Result findActualPrice(@RequestBody Map<String, Object> searchMap){
        return new Result(true,StatusCode.OK,"查询成功",goodsService.findActualPrice(searchMap));
    }


    @GetMapping("/findAll")
    @ApiOperation(value = "查询全部数据",notes = "查询全部数据")
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",goodsService.findAll());
    }

    @GetMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询数据",notes = "根据id查询数据")
    public Result findById(@PathVariable("id")Integer id){
        return new Result(true,StatusCode.OK,"查询成功",goodsService.findById(id));
    }


    @PostMapping("/search/{page}/{size}")
    @ApiOperation(value = "根据条件分页",notes = "根据条件分页")
    public Result findPage(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
        return  new Result(true, StatusCode.OK,"查询成功", goodsService.findPage(searchMap, page, size)  );
    }
    @GetMapping("/search/{page}/{size}")
    @ApiOperation(value = "分页",notes = "分页")
    public Result findPage(@PathVariable int page, @PathVariable int size){
        return  new Result(true,StatusCode.OK,"查询成功", goodsService.findPage(page, size) );
    }


    @PostMapping("/search")
    @ApiOperation(value = "根据条件查询",notes = "根据条件查询")
    public Result findList( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",goodsService.findList(searchMap));
    }

    @ApiOperation(value = "根据id删除用户",notes = "根据id删除用户")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id ){
        goodsService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    @ApiOperation(value = "根据id逻辑删除用户",notes = "根据id逻辑删除用户")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id ){
        goodsService.deleteById(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @ApiOperation(value = "添加基础数据",notes = "添加基础数据")
    @PostMapping("/save")
    public  Result save(@RequestBody Goods goods){
        goodsService.add(goods);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    @ApiOperation(value = "修改基础数据",notes = "修改基础数据")
    @PutMapping("/update")
    public Result update(@RequestBody Goods goods){
        goodsService.update(goods);
        return new Result(true,StatusCode.OK,"添加成功");
    }




}
