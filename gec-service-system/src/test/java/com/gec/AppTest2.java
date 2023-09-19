package com.gec;

import com.gec.model.system.SysRole;
import com.gec.system.ServiceApp;
import com.gec.system.service.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApp.class)
public class AppTest2 {

    @Autowired
  private SysRoleService sysRoleService;


    @Test
    public void query1(){
        List<SysRole> list = sysRoleService.list();
        for (SysRole role : list) {
            System.out.println("role = " + role);
        }
    }

    @Test
    public void save(){
        SysRole role = new SysRole();
        role.setRoleName("测试2");
        role.setDescription("测试用的2");
        role.setRoleCode("300");
        boolean isSave = sysRoleService.save(role);
        System.out.println("isSave = " + isSave);
    }

    @Test
    public void updateRole2(){
        SysRole role = new SysRole();
        role.setRoleName("xixi");
        role.setDescription("测试用的666");
        role.setRoleCode("300");
        role.setId(20L);

        //执行修改操作
        boolean isUpdate = sysRoleService.updateById(role);
        System.out.println("isUpdate = " + isUpdate);
    }


    //根据id删除单个角色信息
    @Test
    public void deleteById(){
        //配置了逻辑删除，这里就只做了修改，把0修改为1
        boolean isdel = sysRoleService.removeById(21);
        System.out.println("isdel = " + isdel);
    }


    //批量删除多个角色信息
    @Test
    public void batchDeleteByIds(){
        List<Integer> list = Arrays.asList(18, 19, 20);
        boolean isDel = sysRoleService.removeByIds(list);
        System.out.println("isDel = " + isDel);
    }
}
