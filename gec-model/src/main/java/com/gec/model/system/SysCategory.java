package com.gec.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gec.model.base.BaseEntity;
import lombok.Data;

//栏目
@Data
@TableName("sys_category")
public class SysCategory extends BaseEntity{

    private String name;//栏目名
}
