package com.webuilding.util;

import com.alibaba.fastjson2.JSONObject;
import com.webuilding.common.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient发送GET、POST请求
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
    /**
     * 返回成功状态码
     */
    private static final int SUCCESS_CODE = 200;

    /**
     * 发送GET请求
     * @param url   请求url
     * @return JSON或者字符串
     * @throws Exception
     */
    public static JSONObject sendGet(String url) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();//创建HttpClient对象
            URIBuilder uriBuilder = new URIBuilder(url);//创建URIBuilder
            HttpGet httpGet = new HttpGet(uriBuilder.build());//创建HttpGet
            //设置请求头部编码
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            // 设置返回编码
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            //请求服务
            response = client.execute(httpGet);
            //获取响应吗
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                //获取返回对象
                HttpEntity entity = response.getEntity();
                //通过EntityUitls获取返回内容
                String result = EntityUtils.toString(entity,"UTF-8");
                //转换成json,根据合法性返回json或者字符串
                jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }else{
                LOGGER.error("HttpClientUtils-line: {}-{}, errorMsg{}",statusCode,url, "GET请求失败！");
                throw new Exception("接口调用失败:"+statusCode);
            }
        }catch (Exception e){
            LOGGER.error("HttpClientUtils-line: {}, Exception: {}", url, e);
            throw e;
        } finally {
            response.close();
            client.close();
        }
    }

    /**
     * 发送POST请求
     * @param url
     * @param nameValuePairList
     * @return JSON或者字符串
     * @throws Exception
     */
    public static com.alibaba.fastjson2.JSONObject sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();//创建一个httpclient对象
            HttpPost post = new HttpPost(url);//创建一个post对象
            //包装成一个Entity对象
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            //设置请求的内容
            post.setEntity(entity);
            //设置请求的报文头部的编码
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
            //执行post请求
            response = client.execute(post);
            //获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                //通过EntityUitls获取返回内容
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                //转换成json,根据合法性返回json或者字符串
                jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }else{
                LOGGER.error("HttpClientUtils-line: {}-{}, errorMsg{}",statusCode,url, "POST请求失败！");
                throw new Exception("接口调用失败:"+statusCode);
            }
        }catch (Exception e){
            LOGGER.error("HttpClientUtils-line: {}, Exception：{}", url, e);
            throw e;
        }finally {
            response.close();
            client.close();
        }
    }

    /**
     * 发送POST请求
     * @param url
     * @param params
     * @return JSON或者字符串
     * @throws Exception
     */
    public static JSONObject sendPost(String url, String params) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();//创建一个httpclient对象
            HttpPost post = new HttpPost(url);//创建一个post对象

            post.addHeader("Content-type","application/json; charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(params, Charset.forName("UTF-8")));

            //执行post请求
            response = client.execute(post);
            //获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                //通过EntityUitls获取返回内容
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                //转换成json,根据合法性返回json或者字符串
                jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }else{
                LOGGER.error("HttpClientUtils-line: {}-{}, errorMsg{}",statusCode,url, "Post请求失败！");
                throw new Exception("接口调用失败:"+statusCode);
            }
        }catch (Exception e){
            LOGGER.error("HttpClientUtils-line: {}, Exception：{}", url, e);
            throw e;
        }finally {
            response.close();
            client.close();
        }
    }

    /**
     * @param params    参数名数组
     * @return 参数对象
     */
    public static List<NameValuePair> getParams(Map<String,String> params){
        if(params == null){
            return null;
        }
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        for(String key : params.keySet()){
            nameValuePairList.add(new BasicNameValuePair(key,params.get(key)));
        }
        return nameValuePairList;
    }

    public static void main(String[] args){
        //116.7.229.147 www.tzdswy.com
        String companyName = "深圳市投资控股有限公司_深投控 -党办";
        try{
            String url = "http://120.24.225.137:8090" + Constant.WEIXIN_URL_SEND_PHONE_MESSAGE + "?phones=15353492476&id=482&companyName="+URLEncoder.encode(companyName, "utf-8");//请求地址
            JSONObject result = HttpClientUtils.sendGet(url);
            System.out.println("result:"+result.toJSONString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}