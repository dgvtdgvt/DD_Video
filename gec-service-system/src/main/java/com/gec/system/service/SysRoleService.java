package com.gec.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.model.system.SysRole;
import com.gec.model.vo.AssginRoleVo;
import com.gec.model.vo.SysRoleQueryVo;

import java.util.Map;

/**
 * 接口
 */
public interface SysRoleService extends IService<SysRole> {
    //抽象方法，没有方法体
    IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo roleQueryVo);

    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String, Object> getRolesByUserId(Long userId);

    void doAssign(AssginRoleVo assginRoleVo);
}