package com.gec;

import com.gec.system.exception.MyCustomerException;

import java.util.Date;

/**
 * @Author:gec
 * @Date:2023/6/20
 * @Description:code
 * @version:1.0
 */
public class ExceptionTest {

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println("date = " + date);
        long time = date.getTime();
        System.out.println("time = " + time);

        long time2 = System.currentTimeMillis();
        System.out.println("time2 = " + time2);
    }

    private static void fun1() {
        System.out.println("1111");
        int a = 10;
        if(a>15){
            throw new MyCustomerException(500,"出现异常了");
        }

        System.out.println("6666");
    }
}
