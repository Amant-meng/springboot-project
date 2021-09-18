package com.ym.springbootproject.moudle.sys.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;


@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Data
@TableName("person")
public class Person {

    private int id;
    @Excel(name = "姓名",width = 25)
    private String name;

    @Excel(name = "性别",width = 25)
    private String sex;

    @Excel(name = "年龄",width = 25)
    private int age;

    @Excel(name = "电话",width = 25)
    private String phone;

    @Excel(name = "身份证号",width = 25)
    private String idcard;

    @Excel(name = "生日",exportFormat = "yyyy-MM-dd",width = 25,orderNum = "1")
    private Date birthday;

    @Excel(name = "电子邮件",width = 25)
    private String email;

    @Excel(name = "QQ",width = 30)
    private String qq;

    @Excel(name = "微信",width = 30)
    private String wechat;
    private String address;
    private Date createTime;
    private Date updateTime;
    private String delflag;
    private String remark;


}
