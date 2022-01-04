package com.ym.springbootproject.moudle.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.moudle.sys.entity.Person;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PersonService extends IService<Person> {

    List<Map> selectPerson();

    List<Person> selectPersonList();

    Set<String> getUserPermissions();

    Person getUserByName(String name);

    ResultBody addPerson(List<Person> personList);

    ResultBody updatePerson(List<Person> personList);
}
