package com.ym.springbootproject.moudle.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.sys.mapper.PersonMapper;
import com.ym.springbootproject.moudle.sys.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Override
    public Set<String> getUserPermissions() {
        return null;
    }

    @Override
    public Person getUserByName(String name) {
        return null;
    }

    /**
     * 添加人员
     * @param personList
     * @return
     */
    @Override
    public ResultBody addPerson(List<Person> personList) {
        try{
            personList.forEach(person->{
                personMapper.insert(person);
            });
            return ResultBody.ok();
        }catch (Exception e){
            return ResultBody.error(e.getMessage());
        }

    }

    @Override
    public ResultBody updatePerson(List<Person> personList) {
        personList.forEach(person -> personMapper.updateById(person));
        return ResultBody.ok();
    }


}
