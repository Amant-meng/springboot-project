package com.ym.springbootproject.moudle.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Meng
 * @Description: TODO
 * @date 2021/9/18 16:08
 */
@TableName("sys_dict")
@Data
public class SysDict {

    @TableField("id")
    @TableId(type = IdType.AUTO)
    private int id;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("parent_code")
    private String parentCode;

    @TableField("order_Num")
    private int orderNum;

    @TableField("remark")
    private String remark;

    @TableField("longitude")
    private String longitude;

    @TableField("latitude")
    private String latitude;

    @TableField("full_Name")
    private String fullName;

    @TableField("full_spell")
    private String fullSpell;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("delflag")
    private String delflag;

}
