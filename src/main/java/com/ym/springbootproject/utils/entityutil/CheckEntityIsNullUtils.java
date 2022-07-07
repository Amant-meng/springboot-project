package com.ym.springbootproject.utils.entityutil;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @Description TODO
 * @Author yangmeng 检查实体类所有字段属性是否为空
 * @Date 2022/4/29 10:07
 */
public class CheckEntityIsNullUtils {

    /**
     * 检查实体所有属性是否为空
     * 为空返回 true，不为空返回 false
     * @param object
     * @return
     */
    public static boolean checkObject(Object object){
        if(object == null){
            return true;
        }
        try {
            for (Field field:object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                System.out.print(field.getName()+":");
                System.out.println(field.get(object));
                if(!field.getName().equals("serialVersionUID")){
                    if(field.get(object) !=null && StringUtils.isNotBlank(field.get(object).toString())){
                        return false;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
