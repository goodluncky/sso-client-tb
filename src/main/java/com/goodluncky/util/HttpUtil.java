package com.goodluncky.util;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpUtil {
    public  static String sendHttpRequest(String httpURL, Map<String,String> params) throws Exception {
        //1.定义需要访问的地址
        URL url = new URL(httpURL);
        //2.连接URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //3.请求方式
        connection.setRequestMethod("POST");
        //4.携带参数
        connection.setDoOutput(true);

        if (params != null && params.size() > 0){
            StringBuilder sb=new StringBuilder();
            for (Map.Entry<String,String> param:params.entrySet()){
                sb.append("&").append(param.getKey()).append("=").append(param.getValue());
            }
            connection.getOutputStream().write(sb.substring(1).toString().getBytes("UTF-8"));
        }



        //5.发起请求
        connection.connect();
        //6.接收返回值
        String response = StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));

       return response;
    }
}
