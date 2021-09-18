package com.ym.springbootproject.moudle.es.service;

import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.es.entity.SearchParam;
import org.elasticsearch.search.SearchHit;

import java.util.List;
import java.util.Map;

public interface EsTestService {

    boolean addPlayer();

    boolean addPerson(Person person, String index, String id);

    boolean importAll(SearchParam param);

    List<Person> searchMatch(String key, String value);

    Map<String,Object> selectEsDataById(SearchParam param);

    boolean updatePlayer(Person person, String id);

    boolean deletePlayer(String id);

    SearchHit[] getDataById(SearchParam param);

    void add(SearchParam param);

}
