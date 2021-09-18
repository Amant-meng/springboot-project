package com.ym.springbootproject.moudle.es.controller;

import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.es.entity.SearchParam;
import com.ym.springbootproject.moudle.es.service.EsTestService;
import io.swagger.annotations.Api;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  ES数据添加控制层
 * @author yangmeng
 * @Date 2021/6/24
 */
@Api(value = "ES数据接口",tags = {"ES数据接口"})
@RestController
@RequestMapping("/elasticsearch")
public class EsTestController {

    @Autowired
    private EsTestService esTestService;

    /**
     * ES添加数据测试
     */
    @PostMapping("/addPlayer")
    public ResultBody addPlayer(){

        esTestService.addPlayer();
        return ResultBody.ok();
    }

    /**
     * 查询数据库人员信息往ES库添加
     */
    @PostMapping("/importAll")
    public ResultBody importAll(@RequestBody SearchParam param){
        esTestService.importAll(param);
        return ResultBody.ok();
    }

    /**
     *  ES数据查询，查询指定索引下指定数据
     * @param param
     * @return
     */
    @GetMapping("/selectEsDataById")
    public ResultBody getPlayer(@RequestBody SearchParam param){

        return ResultBody.ok(esTestService.selectEsDataById(param));
    }

    /**
     * 通过姓名去查询人员信息
     * @param name
     * @return
     */
    @RequestMapping("/searchMatchByName")
    public List<Person> searchMatchByName(@RequestParam(value = "name", required = false) String name) {

        return esTestService.searchMatch("name",name);
    }

    /**
     * 更新ES数据
     * @param person
     * @param id
     * @return
     */
    @PostMapping("updatePlayer")
    public ResultBody updatePlayer(@RequestBody Person person,String id){
        esTestService.updatePlayer(person,id);
        return ResultBody.ok();
    }

    /**
     * 删除ES数据
     * @param id
     * @return
     */
    @DeleteMapping("/deletePlayer")
    public ResultBody deletePlayer(@PathVariable String id){
        esTestService.deletePlayer(id);
        return ResultBody.ok();
    }


    /**
     * 根据id查询ES对应的数据
     * @param param
     * @return
     */
    @PostMapping("/getDataById")
    public SearchHit[] getDataById(@RequestBody SearchParam param) {

        return esTestService.getDataById(param);
    }

    /**
     * 往ES里插入数据
     * @param param
     * @return
             */
    @PostMapping("/add")
    public ResultBody add(@RequestBody SearchParam param) {
        esTestService.add(param);
        return ResultBody.ok();
    }




}
