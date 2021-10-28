package com.ym.springbootproject.moudle.sys.controller;

import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.sys.service.PersonService;
import com.ym.springbootproject.moudle.sys.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
