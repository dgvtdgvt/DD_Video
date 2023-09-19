package com.gec;

import com.gec.system.util.JwtHelper;
import com.gec.system.util.MD5Helper;
import org.junit.Test;

/**
 * @Author:gec
 * @Date:2023/6/21
 * @Description:code
 * @version:1.0
 */
public class TokenTest {

    @Test
    public void fun1(){
        // a.生成 jwt token
        String token = JwtHelper.createToken("1001", "admin");
        System.out.println(token);

        //b. 从 token 中解密获取 userId
        String userId = JwtHelper.getUserId(token);
        System.out.println(userId);



        //c.从 token 中解密获取 username
        String username = JwtHelper.getUsername(token);
        System.out.println(username);
    }

    @Test
    public void fun2(){
        //96e79218965eb72c92a549dd5a330112
        //96e79218965eb72c92a549dd5a330112 //数据库当中
        String pwd = "123456"; //明文
        String md5pwd = MD5Helper.encrypt(pwd); //密文
        System.out.println("md5pwd = " + md5pwd);

    }
}
