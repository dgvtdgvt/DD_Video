package com.gec;

import com.gec.model.system.SysRole;
import com.gec.system.ServiceApp;
import com.gec.system.mapper.SysRoleMapper;
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
public class AppTest {

    @Autowired
   private SysRoleMapper sysRoleMapper;

    @Test
    public void query1(){
        List<SysRole> list = sysRoleMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void insert(){
        SysRole role = new SysRole();
        role.setRoleName("测试1");
        role.setDescription("测试用的");
        role.setRoleCode("200");
        int iret = sysRoleMapper.insert(role);
        System.out.println("iret = " + iret);
    }

    @Test
    public void updateRole(){
        //先查询出要修改的角色信息
        SysRole role = sysRoleMapper.selectById(20);

        //修改role中的内容
        role.setRoleName("老板");

        //执行修改操作
        int iret = sysRoleMapper.updateById(role);
        System.out.println("iret = " + iret);
    }

    //根据id删除单个角色信息
    @Test
    public void deleteById(){
        //配置了逻辑删除，这里就只做了修改，把0修改为1
        int iret = sysRoleMapper.deleteById(20);
        System.out.println("iret = " + iret);
    }

    //批量删除多个角色信息
    @Test
    public void batchDeleteByIds(){
        List<Integer> list = Arrays.asList(18, 19, 20);
        int iret = sysRoleMapper.deleteBatchIds(list);
        System.out.println("iret = " + iret);
    }
}
