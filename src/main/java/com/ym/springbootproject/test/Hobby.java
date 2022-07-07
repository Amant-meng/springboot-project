package com.ym.springbootproject.test;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

/**
 * @Description TODO
 * @Author yangmeng
 * @Date 2022/6/23 14:11
 */
@Data
@ExcelTarget("hobby")
public class Hobby {

    private String id;

    @Excel(name = "爱好")
    private String  name;

    @Excel(name = "内容")
    private String  text;
}
