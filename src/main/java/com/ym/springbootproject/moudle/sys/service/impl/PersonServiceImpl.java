package com.ym.springbootproject.moudle.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.sys.mapper.PersonMapper;
import com.ym.springbootproject.moudle.sys.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 人员信息接口实现类
 * @Author yangmeng
 * @date 2021/6/25
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService{

    @Autowired
    private PersonMapper personMapper;

    /**
     * 查询人员信息
     * @return
     */
    @Override
    public List<Map> selectPerson() {
        return personMapper.selectPerson();
    }

    @Override
    public List<Person> selectPersonList() {
        return personMapper.selectAll();
    }


}
