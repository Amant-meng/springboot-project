package com.ym.springbootproject.moudle.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ym.springbootproject.moudle.sys.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PersonMapper extends BaseMapper<Person> {

    @Select("select * from person")
    List<Person> selectAll();

    List<Map> selectPerson();

}
