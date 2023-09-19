package com.gec.system.controller;

import com.gec.model.system.SysUser;
import com.gec.model.vo.LoginVo;
import com.gec.system.exception.MyCustomerException;
import com.gec.system.service.SysUserService;
import com.gec.system.util.JwtHelper;
import com.gec.system.util.MD5Helper;
import com.gec.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:gec
 * @Date:2023/6/18
 * @Description:code
 * @version:1.0
 */
@Api(tags = "登录管理控制器")
@RestController
@RequestMapping(value = "/admin/system/index") //一级请求映射地址
public class LoginController {

    @Autowired
    private SysUserService sysUserService;


    //登录的接口
    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){

        //调用业务逻辑层中的方法
        //通过用户名去查询用户的对象信息
        SysUser sysUser = sysUserService.getUserInfoUserName(loginVo.getUsername());
        //判断sysUser对象是否为空
        if(sysUser==null){
            throw new MyCustomerException(20001,"此用户名不存在！");
        }

        //得到前端传递过来的明文密码
        String password = loginVo.getPassword();
        //加密成密文
        String encryptPwd = MD5Helper.encrypt(password);
        System.out.println("encryptPwd = " + encryptPwd);

        String pwd_db = sysUser.getPassword();
        boolean isOk = pwd_db.equals(encryptPwd);
        System.out.println("isOk:" +isOk);

        //跟sysUser中的密码进行比较
        if(!isOk){
            throw new MyCustomerException(20001,"密码不正确！");
        }

        //判断当前用户的状态是不是禁用状态
        if(sysUser.getStatus().intValue()==0){ //0表示停用状态
            throw new MyCustomerException(20001,"状态不可用！");
        }

        //通过userId和username生成一个Jwt令牌
        String token = JwtHelper.createToken(sysUser.getId().toString(), sysUser.getUsername());

        //把生成的token令牌令牌封装成前端规定的格式
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    //获得当前登录的用户的相关信息
    @ApiOperation("获得登录的用户信息")
    @GetMapping("/info")
    public Result getInfo(HttpServletRequest request){
        //从头令牌中得到token
        String token = request.getHeader("token");
        System.out.println("token = " + token);
        //从token中得到用户名
        String username = JwtHelper.getUsername(token);
        System.out.println("username = " + username);
        //String username = "lisi";
        //通过用户从数据库查询出用户的各方面的数据,用户信息、菜单信息、按钮的信息
        Map<String,Object> map = this.sysUserService.getUserInfo(username);
        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
