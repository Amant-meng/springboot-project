package com.ym.springbootproject.moudle.es.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.es.entity.SearchParam;
import com.ym.springbootproject.moudle.sys.mapper.PersonMapper;
import com.ym.springbootproject.moudle.es.service.EsTestService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static javax.swing.plaf.synth.ColorType.MAX_COUNT;

@Service
public class EsTestServiceImpl implements EsTestService {

    private static final Logger log = LoggerFactory.getLogger(EsTestServiceImpl.class);

    @Resource
    private RestHighLevelClient client;

    @Autowired
    private PersonMapper personMapper;

    /**
     * ES添加数据测试
     * @return
     */
    @Override
    public boolean addPlayer() {

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "王五");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "王五今天上班迟到");
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("3").source(jsonMap);

        IndexResponse response = null;
        try {
            response = client.index(indexRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("addPlayer {}", JSONObject.toJSON(response));
        return true;
    }

    /**
     * 添加用户，通过数据库查询数据往ES库添加信息
     * @param person
     * @param id
     * @return
     */
    @Override
    public boolean addPerson(Person person, String index,String id) {
        IndexRequest request = new IndexRequest(index).id(id).source(beanToMap(person));
        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("addPerson {}", JSONObject.toJSON(response));
        return false;
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if(beanMap.get(key) != null)
                    map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }


    /**
     * 从Mysql数据库查询数据添加ES库
     * @return
     */
    @Override
    public boolean importAll(SearchParam param){
        List<Person> list = personMapper.selectAll();
        int count = 1;
        for(Person person: list){
            addPerson(person,param.getIndex(),String.valueOf(person.getId()));
            log.info("importAll {} : {}", count++, person);
        }
        return true;
    }

    /**
     * 通过姓名去查询人员信息
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public List<Person> searchMatch(String key,String value){
        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(key,value));
        searchSourceBuilder.size(MAX_COUNT);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("searchMatch {}", JSONObject.toJSON(response));

        SearchHit[] hits = response.getHits().getHits();
        List<Person> personList = new LinkedList<>();
        for(SearchHit hit: hits){
            Person person = JSONObject.parseObject(hit.getSourceAsString(),Person.class);
            personList.add(person);
        }

        return personList;
    }

    /**
     *
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> selectEsDataById(SearchParam param) {
        GetRequest getRequest = new GetRequest(param.getIndex(),param.getId());
        GetResponse response = null;
        try {
            response = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("selectEsDataById {}", JSONObject.toJSON(response));
        return response.getSource();
    }

    /**
     * 更新ES数据信息
     * @param person
     * @param id
     * @return
     */
    @Override
    public boolean updatePlayer(Person person, String id) {
        UpdateRequest request = new UpdateRequest("posts",id).doc(person);
        UpdateResponse response = null;
        try {
            response = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {e.printStackTrace();

        }
        log.info("updatePlayer {}", JSONObject.toJSON(response));
        return true;
    }

    /**
     * 删除ES数据信息
     * @param id
     * @return
     */
    @Override
    public boolean deletePlayer(String id) {
        DeleteRequest request = new DeleteRequest("posts",id);
        DeleteResponse response = null;
        try {
            response = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("deletePlayer {}", JSONObject.toJSON(response));
        return true;
    }


    /**
     * 根据id查询ES对应的数据
     * @param param
     * @return
     */
    @Override
    public SearchHit[] getDataById(SearchParam param) {
        //拼接查询内容
        /*String body = param.getIndex() + "/" + param.getType() + "/" + param.getId();
        Request request = new Request("GET", body);*/
        JSONObject jsonObject = new JSONObject();
        SearchHit[] hits = null;
        try {
            //GetRequest getRequest = new GetRequest(param.getIndex(),param.getId());
            SearchRequest searchRequest = new SearchRequest(param.getIndex());
            //GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
            SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            log.info("getDataById {}", JSONObject.toJSON(searchResponse));
            JSONObject json = JSONObject.parseObject(String.valueOf(searchResponse));
            //获取我们需要的内容
            hits = searchResponse.getHits().getHits();
            for (SearchHit hit:hits) {
                jsonObject = JSONObject.parseObject(hit.getSourceAsString());

            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return hits;

    }

    /**
     * 往ES里插入数据
     * @param param
     * @return
     */
    @Override
    public void add(SearchParam param) {
        Person person = new Person();
        person.setName("朱元璋");
        person.setAddress("安徽凤阳");
        person.setBirthday(new Date());
        person.setPhone("16666203213");
        person.setId(123);
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(person);
            IndexRequest indexRequest = new IndexRequest(param.getIndex())
                    .id(param.getId()).source(jsonObject);
            IndexResponse response = client.index(indexRequest,RequestOptions.DEFAULT);

            log.info("add {}", JSONObject.toJSON(response));
            //获取响应体
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }



}
