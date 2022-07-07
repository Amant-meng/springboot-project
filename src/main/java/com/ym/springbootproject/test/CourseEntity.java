package com.ym.springbootproject.test;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author yangmeng
 * @Date 2022/6/23 13:18
 */
@Data
@ExcelTarget("courseEntity")
public class CourseEntity {

    /** 主键 */
    private String  id;
    /** 课程名称 */
    @Excel(name = "课程名称", orderNum = "1", width = 25)
    private String  name;
    /** 老师主键 */
    @ExcelEntity(id = "absent")
    private TeacherEntity mathTeacher;

    @ExcelCollection(name = "学生", orderNum = "4")
    private List<UserEntity> students;

    @ExcelCollection(name = "爱好", orderNum = "4")
    private List<Hobby> hobby;
}
