package com.ym.springbootproject.moudle.sys.controller;

import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.SysDict;
import com.ym.springbootproject.moudle.sys.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Meng
 * @Description: TODO 系统字典类
 * @date 2021/9/18 15:55
 */
@Api(value = "系统字典接口",tags = {"系统字典接口"})
@RestController
@RequestMapping("/sysDict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation("根据字典类型获取字典数据")
    @GetMapping("/getSysDictByType")
    public ResultBody getSysDictByType(@RequestParam String type){
        List<Map<String,String>> mapList = sysDictService.getSysDictByType(type);
        return ResultBody.ok(mapList);
    }

    @GetMapping("/sysDictDataClear")
    public ResultBody sysDictDataClear(){
        return sysDictService.sysDictDataClear();
    }

    @GetMapping("/select")
    public ResultBody select(){
        return ResultBody.ok(sysDictService.select());
    }

    @PostMapping("/selectPage")
    public ResultBody selectPage(@RequestBody SysDict sysDict){
        return ResultBody.ok(sysDictService.selectPage(sysDict));
    }
}
