package com.ym.springbootproject.test;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

/**
 * @Description TODO
 * @Author yangmeng
 * @Date 2022/6/23 13:19
 */
@Data
@ExcelTarget("teacherEntity")
public class TeacherEntity {

    private String id;
    /** name */

    @Excel(name = "主讲老师_major,代课老师_absent", orderNum = "1",needMerge = true, isImportField = "true_major,true_absent")
    private String name;
}
