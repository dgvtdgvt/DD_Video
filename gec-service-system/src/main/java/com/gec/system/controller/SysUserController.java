package com.gec.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.model.system.SysUser;
import com.gec.model.vo.SysUserQueryVo;
import com.gec.system.service.SysUserService;
import com.gec.system.util.MD5Helper;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gec
 * @since 2023-06-19
 */
@Api(tags = "用户管理控制器")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;


    @ApiOperation("用户的分页和条件查询")
    @GetMapping("/{page}/{limit}")
    public Result selectUserPageByVo(@PathVariable Long page,
                                     @PathVariable Long limit,
                                     SysUserQueryVo sysUserQueryVo){

        //调用业务逻辑层中分页的方法
        Page<SysUser> page1 = new Page<>(page, limit);
        IPage<SysUser> page2 = sysUserService.selectPage(page1, sysUserQueryVo);
        return Result.ok(page2);
    }

    @ApiOperation("添加用户信息")
    @PostMapping("addUser")
    public Result addSysUser(@RequestBody SysUser sysUser){

        //获得传递进来的用户的密码
        String password = sysUser.getPassword();
        //对密码进行加密处理
        String encryptPwd = MD5Helper.encrypt(password);
        //把加密后的密码封装回去
        sysUser.setPassword(encryptPwd);

        boolean isSave = sysUserService.save(sysUser);
        return isSave?Result.ok():Result.fail();
    }

    @ApiOperation("根据id获得用户信息")
    @GetMapping("findUserById/{id}")
    public Result getUserById(@PathVariable Long id){
        SysUser sysUser = this.sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody SysUser sysUser){
        boolean isEdit = sysUserService.updateById(sysUser);
        return isEdit?Result.ok():Result.fail();
    }

    @ApiOperation("根据id删除单个用户信息")
    @GetMapping("removeUserById/{id}")
    public Result deleteByid(@PathVariable Long id){
        boolean isDel = sysUserService.removeById(id);
        return isDel?Result.ok():Result.fail();
    }

    @ApiOperation("批量删除用户信息")
    @DeleteMapping("removeBatchUserByIds")
    public Result removeBatchUserByIds(@RequestBody List<Long> ids){
        boolean isDelAll = this.sysUserService.removeByIds(ids);
        return isDelAll?Result.ok():Result.fail();
    }

    @ApiOperation("更改用户状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Integer id,@PathVariable Integer status){
        boolean isEdit = this.sysUserService.updateStatus(id,status);
        return isEdit?Result.ok():Result.fail();
    }
}

