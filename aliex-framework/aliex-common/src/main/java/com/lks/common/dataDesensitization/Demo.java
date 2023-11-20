package com.lks.common.dataDesensitization;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lks.common.dataDesensitization.pojo.Person;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.PanelUI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lks
 * @description 数据脱敏
 * @e-mail 1056224715@qq.com
 * @date 2023/11/12 21:27
 */
public class Demo {


    private static Logger logger = LoggerFactory.getLogger(Demo.class);


    public static String toJSONString(Object object) {
       return null;
    }

    public static void main(String[] args) {
        Map<String,Object> personMap = new HashMap<>();
        personMap.put("name","张无忌");
        personMap.put("phone","17709141590");
        personMap.put("account","14669037943256249");
        personMap.put("id",1L);

        Map<String,Object> innerMap = new HashMap();
        innerMap.put("name","张无忌的女儿");
        innerMap.put("phone","18809141567");
        innerMap.put("account","17869037943255678");
        innerMap.put("id",2L);
        personMap.put("daughter",innerMap);

        System.out.println("脱敏后:"+JSON.toJSONString(personMap));
        System.out.println("脱敏后的原始Map对象:"+personMap);

        Person person = new Person();
        person.setAge(1);
        person.setEmail("fdsfdsaa");
        person.setPhoneNumber("1123434234321");


        System.out.println(JSON.toJSONString(person));

    }
}
