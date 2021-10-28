package com.ym.springbootproject.moudle.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.SysDict;
import com.ym.springbootproject.moudle.sys.mapper.SysDictMapper;
import com.ym.springbootproject.moudle.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Meng
 * @Description: TODO
 * @date 2021/9/18 16:05
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper,SysDict> implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 通过字典类型获取字典数据
     * @param type
     * @return
     */
    @Override
    public List<Map<String,String>> getSysDictByType(String type) {
        List<Map<String,String>> resultList = sysDictMapper.getSysDictByType(type);
        return resultList;
    }

    @Override
    public List<SysDict> select(){
        List<SysDict> list = this.lambdaQuery()
                .select(SysDict::getCode, SysDict::getName, SysDict::getParentCode)
                .eq(SysDict::getDelflag, "0")
                .list();

        return list;
    }


    @Override
    public IPage<SysDict> selectPage(SysDict sysDict){
        QueryWrapper<SysDict> wrapper = new QueryWrapper();
        wrapper.eq("delflag", "0")
        .select("code","name","parent_code");

        IPage<SysDict> page = this.page(new Page<>(1,10),wrapper);
        return page;
    }

    @Override
    public ResultBody sysDictDataClear() {
        String type = "code_xzqhdm";
        List<Map<String,String>> resultList = sysDictMapper.getSysDictByType(type);
        String parentCode = null;
        for(Map list:resultList){
            String code = (String) list.get("code");
            if((code.substring(0,4)+"00").equals(code)){
                parentCode = code;
            }
            if((code.substring(0,4)+"00").equals(parentCode)){
                SysDict sysDict = new  SysDict();
                sysDict.setParentCode(parentCode);
                sysDict.setId((Integer) list.get("id"));
                sysDictMapper.updateById(sysDict);
            }
        }
        return ResultBody.ok();
    }


}
