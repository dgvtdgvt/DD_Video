package com.gec.system.exception;

import com.gec.system.util.Result;
import com.gec.system.util.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

/**
 * @Author:gec
 * @Date:2023/6/16
 * @Description:code
 * @version:1.0
 * 定义全局的异常处理类
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException(Exception e){
        System.out.println("这里全局异常处理-----");
        e.printStackTrace(); //打印异常的堆栈信息
        return Result.fail().message("执行了全局异常处理...");
    }

    //特定异常处理
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result globalException2(NullPointerException e){
        System.out.println("这是特定异常处理-----");
        e.printStackTrace(); //打印异常的堆栈信息
        return Result.fail().message("执行了特定异常处理...");
    }

    //特定异常处理
    @ExceptionHandler(MyCustomerException.class)
    @ResponseBody
    public Result globalException3(MyCustomerException e){
        System.out.println("这是自定义异常处理-----");
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前操作权限");
    }
}
