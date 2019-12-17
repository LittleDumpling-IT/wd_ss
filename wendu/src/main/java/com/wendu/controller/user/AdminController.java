package com.wendu.controller.user;


import com.wendu.config.jwt.JwtUtil;
import com.wendu.entily.PageResult;
import com.wendu.entily.Result;
import com.wendu.entily.StatusCode;
import com.wendu.model.Admin;
import com.wendu.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

/**
 * @author LittleDumpling
 */
@RestController
@RequestMapping("/admin")
@Api(value = "后台管理",description = "后台管理")
public class AdminController {



    @Autowired
    private AdminService adminService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询用户",notes = "根据id查询用户")
    public Result findById(@PathVariable("id")Integer id){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findById(id));
    }

//    @PostMapping("/login")
//    @ApiOperation(value = "登录",notes = "登录")
//    public Result login(@RequestBody Admin admin){
//        admin = adminService.findByUsername(admin.getUsername());
//        if (null == admin ){
//            return new Result(false,StatusCode.ERROR,"登录失败");
//        }
//        //使得前后端通话操作，采用JWT来实现
//        //生成令牌
//        String token = jwtUtil.createToken(admin.getId()+"",admin.getUsername(), "admin");
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("token",token);
//        map.put("role","admin");
//        return new Result(true,StatusCode.OK,"登录成功",map);
//    }



    @PostMapping("/search/{page}/{size}")
    @ApiOperation(value = "根据条件分页",notes = "根据条件分页")
    public Result findPage(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
        PageResult <Admin> pageList = adminService.findPage(searchMap, page, size);
        return  new Result(true,StatusCode.OK,"查询成功", pageList  );
    }
    @GetMapping("/search/{page}/{size}")
    @ApiOperation(value = "分页",notes = "分页")
    public Result findPage(@PathVariable int page, @PathVariable int size){
        PageResult <Admin> pageList = adminService.findPage(page, size);
        return  new Result(true,StatusCode.OK,"查询成功", pageList  );
    }


    @PostMapping("/search")
    @ApiOperation(value = "根据条件查询",notes = "根据条件查询")
    public Result findList( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findList(searchMap));
    }


    @ApiOperation(value = "添加用户",notes = "添加用户")
    @PostMapping("/")
    public Result add(@RequestBody Admin admin  ){
        adminService.add(admin);
        return new Result(true,StatusCode.OK,"增加成功");
    }


    @ApiOperation(value = "修改用户信息",notes = "修改用户信息")
    @PutMapping("/")
    public Result update(@RequestBody Admin admin ){
        adminService.update(admin);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    @ApiOperation(value = "根据id删除用户",notes = "根据id删除用户")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id ){
        adminService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    @ApiOperation(value = "根据id逻辑删除用户",notes = "根据id逻辑删除用户")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id ){
        adminService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
