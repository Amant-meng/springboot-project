package com.ym.springbootproject.moudle.sys.controller;

import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.common.core.AjaxResult;
import com.ym.springbootproject.common.utils.poi.ExcelUtil;
import com.ym.springbootproject.moudle.sys.entity.SysRole;
import com.ym.springbootproject.moudle.sys.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/test")
@Api(value = "测试接口",tags = {"测试接口"})
public class TestController {

    @Autowired
    private TestService  testService;

    @GetMapping("/select")
    @ApiOperation("查询测试")
    public ResultBody select(){
        List<Map> list = testService.selectData();

        return ResultBody.ok(list);
    }

    /**
     * 获取数据库角色信息列表
     * @return
     */
    @GetMapping("/role")
    @ApiOperation("获取数据库角色信息列表")
    public ResultBody selectUserRole(){
        List<SysRole> list = testService.selectSysRoleList();

        return ResultBody.ok(list);
    }

    /**
     * 测试数据导出功能
     * @return
     */
    @PostMapping("/export")
    @ApiOperation("测试数据导出功能")
    public AjaxResult export() {
        List<SysRole> list = testService.selectSysRoleList();
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        return util.exportExcel(list, "用户数据");
    }



    /**
     * 正则表达式匹配身份证
     * @param str
     * @return
     */
    public static String getIDCard(String str) {
        String pattern = "\\d{17}[\\d|X]|\\d{5}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        String idCard = null;
        while(m.find()) {
            idCard = m.group();
        }
        return idCard;
    }

    public static String getTrueName(String str) {
        String pattern = "[\\u4e00-\\u9fa5]{1,20}";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        String idCard = null;
        while(m.find()) {
            idCard = m.group();
        }
        return idCard;
    }



}
