package com.edu.something.Http;




import com.alibaba.fastjson.JSON;
import com.edu.common.httpresp.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tangzhihao
 * @date 2017/12/21
 */

public class HttpTest {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("username","tang");
        map.put("password","123");

        String resp = HttpClientUtil.doPost("http://127.0.0.1:8848/myspringboot/httpPost.do", map);
        System.out.println(resp);
        HttpResponse httpResponse = JSON.parseObject(resp, HttpResponse.class);
        System.out.println(httpResponse.toString());

    }
}
