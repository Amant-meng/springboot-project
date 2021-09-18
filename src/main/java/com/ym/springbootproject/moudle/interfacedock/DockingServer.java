package com.ym.springbootproject.moudle.interfacedock;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Meng
 * @Description: TODO  外部接口对接编写
 * @date 2021/7/1 10:31
 */
@RestController
@RequestMapping("/dockingServer")
public class DockingServer {

    private static final Logger log = LoggerFactory.getLogger(DockingServer.class);

    public Map<String,Object> queryInfo(String sfzh){
        String invokeUrl = "${invokeUrl}";
        Map<String,String> params = new HashMap<>();

        RestTemplate template = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.add("sender_id","${sender_id}");
        httpHeaders.add("service_id","${service_id}");
        httpHeaders.add("end_user.name","${end_user.name}");
        httpHeaders.add("end_user.id_card","${end_user.id_card}");
        httpHeaders.add("end_user.department","${end_user.department}");
        httpHeaders.add("end_user.certificate","${end_user.certificate}");
        httpHeaders.add("end_user.device_id","${end_user.device_id}");
        httpHeaders.add("timestamp","${timestamp}");
        JSONObject busBody = new JSONObject();

        JSONObject appHeader = new JSONObject();
        busBody.put("app_header",appHeader);

        JSONObject appBody = new JSONObject();
        appBody.put("method","query");
        JSONObject parameters=new JSONObject();
        parameters.put("condition","sfzh = '" + sfzh + "'");
        parameters.put("requiredItems","${requiredItems}");
        parameters.put("sortItems","${sortItems}");
        parameters.put("infoCodeMode","${infoCodeMode}");
        appBody.put("parameters",parameters);
        busBody.put("app_body",appBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(busBody.toJSONString(),httpHeaders);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            ResponseEntity<String> responseEntity = template.exchange(invokeUrl, HttpMethod.POST, requestEntity, String.class);

            //todo 返回数据对象
            JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
            if(jsonObject == null){
                resultMap.put("message","调用对方接口服务出现异常");
                return resultMap;
            }
            // 获取结果
            JSONObject resultObject = jsonObject.getJSONObject("app_body");
            if(resultObject == null){
                return resultMap;
            }
            //TODO 获取数据
            List<String> list = (List<String>) resultObject.get("result");
            List<String> columnsList = new ArrayList<>();
            List dataList = new ArrayList<>();
            if(resultObject.get("totalCount") != null){
                String totalCount = resultObject.get("totalCount").toString();
                resultMap.put("totalCount",totalCount);
            }
            //TODO 数据处理
            if(list !=null && list.size()>0){
               //TODO 数据处理入库相关操作

            }

            log.info("StatusCode{}"+ responseEntity.getStatusCode());
            log.info("Headers{}"+ responseEntity.getHeaders());
            log.info("Body{}"+ responseEntity.getBody());
            log.info("status{}"+ responseEntity.getHeaders().get("status"));
            log.info("status_message{}"+ responseEntity.getHeaders().get("status_message"));

            return resultMap;
        }catch (HttpStatusCodeException ex){

            log.error("CanonicalName{}"+ ex.getClass().getCanonicalName());
            log.error("StatusCode{}"+ ex.getStatusCode());
            log.error("task_id{}"+ex.getResponseHeaders().get("task_id"));
            log.error("status{}"+ ex.getResponseHeaders().get("status"));
            log.error("status_message{}"+ ex.getResponseHeaders().get("status_message"));
            log.error("body{}"+ ex.getResponseBodyAsString());

            resultMap.put("CanonicalName", ex.getClass().getCanonicalName());
            resultMap.put("StatusCode",ex.getStatusCode());
            resultMap.put("task_id",ex.getResponseHeaders().get("task_id"));
            resultMap.put("status",ex.getResponseHeaders().get("status"));
            resultMap.put("status_message",ex.getResponseHeaders().get("status_message"));
            resultMap.put("body",ex.getResponseBodyAsString());

            return resultMap;
        }
    }



}
