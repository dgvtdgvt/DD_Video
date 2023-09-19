package com.gec.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:gec
 * @Date:2023/6/16
 * @Description:code
 * @version:1.0
 * 自定义的异常类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCustomerException extends RuntimeException{
    private Integer code;
    private String msg;
}
