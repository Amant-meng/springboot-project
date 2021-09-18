package com.ym.springbootproject.moudle.es.entity;

import lombok.Data;

/**
 * 请求内容实体类
 * @Author ym
 * @date 2021/6/24
 */
@Data
public class SearchParam {

    // 索引
    private String index;
    // 类型
    private String type;
    // id
    private String id;
}
