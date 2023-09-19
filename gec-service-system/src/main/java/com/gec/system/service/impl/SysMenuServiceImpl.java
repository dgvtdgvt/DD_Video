package com.gec.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.model.system.SysMenu;
import com.gec.model.system.SysRoleMenu;
import com.gec.model.vo.AssginMenuVo;
import com.gec.model.vo.RouterVo;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.mapper.SysMenuMapper;
import com.gec.system.mapper.SysRoleMenuMapper;
import com.gec.system.service.SysMenuService;
import com.gec.system.util.MenuHelper;
import com.gec.system.util.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author gec
 * @since 2023-06-20
 */
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<SysMenu> findNodes() {
        //1.获取所有的菜单
        List<SysMenu> sysMenus = this.baseMapper.selectList(null);
        //2.所有菜单数据转换要求数据格式
        List<SysMenu> treeMenus = MenuHelper.bulidTree(sysMenus);
        return treeMenus;
    }

    @Override
    public void removeMenuById(Long id) {
        //查询当前删除菜单下面是否子菜单
        //根据id = parentid
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        //封装查询条件
        wrapper.eq("parent_id", id);

        //查询有多少条数据
        Integer count = this.baseMapper.selectCount(wrapper);
        if(count>0){ //说明要删除的该菜单下面还有子菜单，按道理不能直接删除，必须要先删除子菜单
            //如果进入if了，直接手动抛出了一个异常
            throw new MyCustomerException(201,"请先删除子菜单");
        }

        //执行删除的操作
        this.baseMapper.deleteById(id);

    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        QueryWrapper<SysMenu> wrapper1 = new QueryWrapper<>();
        //封装查询条件
        wrapper1.eq("status", 1); //1：表示的是正常的可用状态

        List<SysMenu> sysMenus = this.baseMapper.selectList(wrapper1);

        //this.baseMapper.selectById() //按照主键来进行查询
        QueryWrapper<SysRoleMenu> wrapper2 = new QueryWrapper<>();
        //封装查询条件
        wrapper2.eq("role_id", roleId);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(wrapper2);

        //获得所有已经分配了的菜单id信息
        List<Long> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu roleMenu : sysRoleMenus) {
            roleMenuIds.add(roleMenu.getMenuId());
        }

        //遍历所有权限列表
        for (SysMenu menu : sysMenus) {
            if(roleMenuIds.contains(menu.getId())){
                //设置该权限已被分配
                menu.setSelect(true);
            }else{
                menu.setSelect(false);
            }
        }

        //将权限列表转换为权限树
        List<SysMenu> sysMenuList = MenuHelper.bulidTree(sysMenus);

        return sysMenuList;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //根据角色id先把旧数据删除掉
        QueryWrapper<SysRoleMenu> delWrapper = new QueryWrapper();
        //获得要删除的角色id
        Long roleId = assginMenuVo.getRoleId();
        delWrapper.eq("role_id", roleId);

        int isDel = sysRoleMenuMapper.delete(delWrapper);

        //把新数据重新插入到数据库当中
        //先获得要插入的菜单的id信息
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        //遍历集合
        for (Long menuId : menuIdList) {
            if(menuId!=null){
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(roleId);

                sysRoleMenuMapper.insert(sysRoleMenu);
            }

        }
    }

    @Override
    public List<RouterVo> findUserMenuList(Long userId) {
        //超级管理员admin账号id为：1
        // 我们约定 admin 是 超级管理员拥有所有的权限
        List<SysMenu> sysMenuList = null;

        if (userId.longValue() == 1) {
            //a.  表示是超级管理员
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));
        } else {

            // b. 其他非超级管理员的 用户
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }



        //c.构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.bulidTree(sysMenuList);

        //TODO  MeunHelper返回的数据属性和 前端路由的属性不一致，所以要处理
        //d.构建路由
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);

        return routerVoList;
    }

    @Override
    public List<String> findUserPermsList(Long id) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (id.longValue() == 1) {
            sysMenuList = this.baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            sysMenuList = this.baseMapper.findMenuListByUserId(id);
        }
        //创建返回的集合
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if(sysMenu.getType() == 2){
                permissionList.add(sysMenu.getPerms());
            }
        }
        return permissionList;
    }
}
