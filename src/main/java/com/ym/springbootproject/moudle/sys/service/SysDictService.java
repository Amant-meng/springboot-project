package com.ym.springbootproject.moudle.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.SysDict;

import java.util.List;
import java.util.Map;

/**
 * @author Meng
 * @Description: TODO
 * @date 2021/9/18 16:05
 */
public interface SysDictService {

    List<Map<String,String>> getSysDictByType(String type);

    ResultBody sysDictDataClear();

    List<SysDict> select();

    IPage<SysDict> selectPage(SysDict sysDict);
}
