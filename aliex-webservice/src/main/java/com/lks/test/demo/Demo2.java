package com.lks.test.demo;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.lks.test.demo.dto.BatchTodoItemReq;
import com.lks.test.demo.dto.TodoItemReq;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/4/6 17:36
 */
public class Demo2 {

    public static void main(String[] args) {
        test();
    }

    public static void test2(){
        String url="http://120.92.112.212:8000/ierp//kapi/app/aeac_mytodo/batchSendTodo";
        String params="{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"todoNum\": \"251\",\n" +
                "            \"callBackUrl\": \"http://192.168.220.15:18080/Services/hf/audit\",\n" +
                "            \"moduleCode\": \"lanxum_approve\",\n" +
                "            \"todoType\": \"todo\",\n" +
                "            \"urgentLevel\": \"6\",\n" +
                "            \"systemCode\": \"lanxum_print\",\n" +
                "            \"creatorId\": \"kezhene\",\n" +
                "            \"todoUrl\": \"http://192.168.220.15:18080/Services/hf/approveDetail?todoNum=251&username=\",\n" +
                "            \"ownerId\": \"simaguang\",\n" +
                "            \"todoTitle\": \"fgdsg\",\n" +
                "            \"sendTime\": \"2023-04-06 17:05:41\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String charset="UTF-8";
        int connectTimeout=10000;
        int readTimeout=10000;
        boolean getLocation=false;

        String ctype= "";
        try {
            String s = HttpsConnection.doPost(url,params, charset, connectTimeout, readTimeout, getLocation);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test(){
        String url = "/kapi/app/aeac_mytodo/batchSendTodo";

        String token = "1503486045847552000_31daFqFk54yUWkKdLeplNg8n29O9JMuOPCRGYJP4rInA3iClho53r7Aa7q1tmUiwGsCBl61ejLiBYp4gKHW7S9H1f1YAOCc7cOM1";
        BatchTodoItemReq req =new BatchTodoItemReq();

        List<TodoItemReq> data = new ArrayList<>();
        TodoItemReq req1 = new TodoItemReq();
        req1.setTodoNum("251");
        req1.setCallBackUrl("http://192.168.220.15:18080/Services/hf/audit");
        data.add(req1);
        req.setData(data);

        String body = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"todoNum\": \"251\",\n" +
                "            \"callBackUrl\": \"http://192.168.220.15:18080/Services/hf/audit\",\n" +
                "            \"moduleCode\": \"lanxum_approve\",\n" +
                "            \"todoType\": \"todo\",\n" +
                "            \"urgentLevel\": \"6\",\n" +
                "            \"systemCode\": \"lanxum_print\",\n" +
                "            \"creatorId\": \"kezhene\",\n" +
                "            \"todoUrl\": \"http://192.168.220.15:18080/Services/hf/approveDetail?todoNum=251&username=\",\n" +
                "            \"ownerId\": \"simaguang\",\n" +
                "            \"todoTitle\": \"fgdsg\",\n" +
                "            \"sendTime\": \"2023-04-06 17:05:41\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String access_token = HttpUtil.createPost(url)
                .header("Content-Type", "application/json")
                .header("access_token", token)
                .body(JSON.toJSONString(req))
                .execute().body();
        System.out.println(access_token);
    }
}
