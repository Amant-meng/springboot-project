package com.ym.springbootproject.moudle.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ym.springbootproject.moudle.sys.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Meng
 * @Description: TODO
 * @date 2021/9/18 16:07
 */
@Repository
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    List<Map<String, String>> getSysDictByType(String type);
}
