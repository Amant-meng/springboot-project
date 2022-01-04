package com.ym.springbootproject.moudle.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.sys.service.PersonService;
import com.ym.springbootproject.moudle.sys.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 人员信息
 * @Author yangmeng
 * @Date  2021/6/25
 **/
@RestController
@RequestMapping("/person")
@Api(value = "人员信息",tags = {"人员信息访问接口"})
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private TestService testService;

    /**
     * 查询人员信息列表
     * @return
     */
    @GetMapping("/selectPerson")
    @ApiOperation(value = "查询人员信息列表")
    public ResultBody selectPerson(){
        List<Map> list = personService.selectPerson();
        return ResultBody.ok(list);
    }

    /**
     * 查询人员信息列表
     * @return
     */
    @GetMapping("/selectPersonList")
    @ApiOperation(value = "查询人员信息列表")
    public ResultBody selectPersonList(){
        List<Person> list = personService.selectPersonList();
        return ResultBody.ok(list);
    }

    /**
     * 添加人员
     * @return
     */
    @PostMapping("/addPerson")
    @ApiOperation(value = "添加人员")
    public ResultBody addPerson(@RequestBody List<Person> personList){
        return personService.addPerson(personList);
    }


    /**
     * 修改人员
     * @return
     */
    @PostMapping("/updatePerson")
    @ApiOperation(value = "修改人员")
    public ResultBody updatePerson(@RequestBody List<Person> personList){
        return personService.updatePerson(personList);
    }


    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("startTime","xxxx");
        map.put("endTime","xxxx");
        map.put("startNum","xxxx");
        map.put("endNum","xxxx");

        JSONObject jsonObject = new JSONObject(map);
        System.out.println(jsonObject);
    }
}
