package com.ym.springbootproject.moudle.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ym.springbootproject.moudle.sys.entity.Person;

import java.util.List;
import java.util.Map;

public interface PersonService extends IService<Person> {

    List<Map> selectPerson();

}
