package com.gec.system.controller;


import com.gec.model.system.SysMenu;
import com.gec.model.vo.AssginMenuVo;
import com.gec.system.service.SysMenuService;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author gec
 * @since 2023-06-20
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @ApiOperation("加载树型菜单列表")
    @GetMapping("findNodes")
    public Result findNodes(){
        List<SysMenu> treeNods = sysMenuService.findNodes();
        return Result.ok(treeNods);
    }

    @ApiOperation("添加菜单")
    @PostMapping("addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        boolean isSave = this.sysMenuService.save(sysMenu);
        return isSave?Result.ok():Result.fail();
    }

    @ApiOperation("根据id查找菜单")
    @GetMapping("findNodeById/{id}")
    public Result findNodeById(@PathVariable Long id){
        SysMenu sysMenu = this.sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation("修改菜单")
    @PostMapping("updateMenu")
    public Result updateMenu(@RequestBody SysMenu sysMenu){
        boolean isEdit = this.sysMenuService.updateById(sysMenu);
        return isEdit?Result.ok():Result.fail();
    }



    @ApiOperation("根据id删除菜单")
    @DeleteMapping("removeMenu/{id}")
    public Result removeMenu(@PathVariable Long id){
        this.sysMenuService.removeMenuById(id);
        return Result.ok();
    }

    @ApiOperation("根据角色id查询菜单信息")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> sysMenuList = this.sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(sysMenuList);
    }


    @ApiOperation("给角色重新分配菜单")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo){
        this.sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }


}

