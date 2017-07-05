package com.yhy.lin.app.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;  
import com.google.gson.JsonObject;
  

public class APIHttpClient {  
  
    // 接口地址 
	//登录接口
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?appLogin";
	//上传订单接口地址
	private static String apiURL = "http://localhost:8080/gwcx/app.do?createOrder";  
    //线路站点信息地址
    //private static String apiURL = "http://localhost:8080/gwcx/app.do?getStationList";  
    //获取机场站点或者火车站站点信息
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?getPTStation";
	//取消订单
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?cancelOrder";
	//完成订单
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?completeOrder";
	//用户意见反馈  
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?feedback";
	//修改用户个人信息
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?updateUserInfo";
	//获取用户个人信息
	//private static String apiURL = "http://localhost:8080/gwcx/app.do?getUserInfo";
	
	
    private Log logger = LogFactory.getLog(this.getClass());  
    private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";  
    private HttpClient httpClient = null;  
    private static APIHttpClient ahc = null;  
    private HttpPost method = null;  
    private long startTime = 0L;  
    private long endTime = 0L;  
    private int status = 0;  
    
//    public static APIHttpClient getInstance(String url){
//    	synchronized(APIHttpClient.class){
//            if(ahc == null){
//            	ahc = new APIHttpClient(url);
//            }
//        }
//    	return ahc;
//    }
    
	/** 
     * 接口地址 
     *  
     * @param url 
     */  
    public APIHttpClient(String url) {  
  
        if (url != null) {
            this.apiURL = url;
        }
        if (apiURL != null) {
            httpClient = new DefaultHttpClient();  
            method = new HttpPost(apiURL);  
        }  
    }  
  
    /** 
     * 调用 API 
     *  
     * @param parameters 
     * @return 
     */  
    public String post(String parameters) {  
        String body = null;  
        logger.info("parameters:" + parameters);  
  
        if (method != null & parameters != null  
                && !"".equals(parameters.trim())) {  
            try {  
  
                // 建立一个NameValuePair数组，用于存储欲传送的参数  
                method.addHeader("Content-type","application/json; charset=utf-8");  
                method.setHeader("Accept", "application/json");  
                method.setEntity(new StringEntity(parameters,"utf-8"));  
                startTime = System.currentTimeMillis();  
                HttpResponse response = httpClient.execute(method);  
                  
                endTime = System.currentTimeMillis();  
                int statusCode = response.getStatusLine().getStatusCode();  
                  
                logger.info("statusCode:" + statusCode);  
                logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));  
                if (statusCode != HttpStatus.SC_OK) {  
                    logger.error("Method failed:" + response.getStatusLine());  
                    status = 1;  
                }  
  
                // Read the response body  
                body = EntityUtils.toString(response.getEntity());  
  
            } catch (IOException e) {  
                // 网络错误  
                status = 3;  
            } finally {  
                logger.info("调用接口状态：" + status);  
            }  
  
        }  
        return body;  
    }  
    
    /** 
     * 调用 API 
     *  
     * @param parameters 
     * @return 
     */  
    public String get() {  
    	
    	String body = "";
        try {  
            // 建立一个NameValuePair数组，用于存储欲传送的参数  
//            method.addHeader("Content-type","application/json; charset=utf-8");  
            startTime = System.currentTimeMillis();  
            HttpGet request = new HttpGet(apiURL);
            HttpResponse response = httpClient.execute(request);  
              
            endTime = System.currentTimeMillis();  
            int statusCode = response.getStatusLine().getStatusCode();  
              
            logger.info("statusCode:" + statusCode);  
            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));  
            if (statusCode != HttpStatus.SC_OK) {  
                logger.error("Method failed:" + response.getStatusLine());  
                status = 1;  
            }  
            // Read the response body  
            body = EntityUtils.toString(response.getEntity());  
            
        } catch (IOException e) {  
            // 网络错误  
        status = 3;  
        } finally {  
            logger.info("调用接口状态：" + status);  
        }  
  
        return body;  
    }  
  
    /** 
     * 调用 API 
     *  
     * @param parameters 
     * @return 
     */  
    public void getQRFile(String path) {
    	
    	boolean b = false;
    	FileOutputStream fileOutputStream = null;
    	InputStream is = null;
    			
        try {  
            // 建立一个NameValuePair数组，用于存储欲传送的参数  
//            method.addHeader("Content-type","application/json; charset=utf-8");  
            startTime = System.currentTimeMillis();  
            HttpGet request = new HttpGet(apiURL);
            HttpResponse response = httpClient.execute(request);  
              
            endTime = System.currentTimeMillis();  
            int statusCode = response.getStatusLine().getStatusCode();  
              
            logger.info("statusCode:" + statusCode);  
            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));  
            if (statusCode != HttpStatus.SC_OK) {  
                logger.error("Method failed:" + response.getStatusLine());  
                status = 1;  
            }  
            
            HttpEntity h = response.getEntity();
            is = h.getContent();
            
            byte[] data = new byte[1024];
            int len = 0;
            
            fileOutputStream = new FileOutputStream(path);
            
            while ((len = is.read(data)) != -1) {
            	fileOutputStream.write(data, 0, len);
            }
            b = true;
        } catch (IOException e) {
        	e.printStackTrace();
            // 网络错误  
        	status = 3;
        } finally {
        	try {
				is.close();
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            logger.info("调用接口状态：" + status);  
        }
//        return b;  
    }  
    
    public static void main(String[] args) {
    	//线路站点信息测试
    	//apiURL += "&serveType=2&cityId=520100";
      	
      	//线路站点信息地址
    	//apiURL += "&serveType=2&cityId=520100&stationId=4028b8815c380cd3015c3823b3f00023&userId=4028820d5beb2c65015beb3fa78b0185";
    	
//    	apiURL = AppGlobals.WECHAT_QR_URL + "?access_token=CbhUuvu4rOt5TIVZ_tFe9F5apAPc93uyZ7c0Bmk79pYSNx3lKVI2ChrIAecJt5pATWQH_"
//    			+ "tv16IWVSuGdbJH9rg8asiLqn1xbmDkLtEL50D5446StWw4nt-wCd8bzalzaQCKhAJAEQY";
//    	String url = AppGlobals.ACCESS_TOKEN_URL.replace("%1", "client_credential").replace("%2", AppGlobals.WECHAT_ID).replace("%3", AppGlobals.WECHAT_APP_SECRET);
//        APIHttpClient ac = new APIHttpClient("https://mp.weixin.qq.com/cgi-bin/showqrcode?"
//        		+ "ticket=gQHr8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAySElsNWs4Wm1jemsxMDAwMGcwM2IAAgS221NZAwQAAAAA");
        			APIHttpClient ac = new APIHttpClient("http://localhost:8080/gwcx/wx.do?wxOauth2");
        JsonArray arry = new JsonArray();
        JsonObject j = new JsonObject();
        System.out.println(ac.get());
        
        
        //登录接口
        j.addProperty("mobile", "15527916902");  
        j.addProperty("code", "1325");  
        
        //接送机订单测试
//        j.addProperty("orderType", "2");  
//        j.addProperty("orderStartingStationId", "4028b8815c380cd3015c3829c1530033");  
//        j.addProperty("orderTerminusStationId", "4028b8815c380cd3015c3823b3f00023");  
//        j.addProperty("orderStartime", "2017-05-28 16:12:00");  
//        j.addProperty("orderExpectedarrival", "2017-05-28 18:12:00");  
//        j.addProperty("orderUnitprice", "20");  
//        j.addProperty("orderNumbers", "2");  
//        j.addProperty("orderContactsname", "张三");  
//        j.addProperty("orderContactsmobile", "15527987654");  
//        j.addProperty("orderFlightnumber", "Z-15");
//        j.addProperty("orderStatus", "1");
//        j.addProperty("orderTrainnumber", "");  
//        j.addProperty("orderPaytype", "1"); 
//        j.addProperty("orderPaystatus", "0");  
//        j.addProperty("orderTerminusStationName", "龙里车站"); 
//        j.addProperty("orderStartingStationName", "一龙洞堡机场"); 
//        j.addProperty("userId", "4028820d5beb2c65015beb3fa78b0185"); 
//        j.addProperty("orderTotalPrice", "40"); 
//        j.addProperty("cityName", "贵阳市"); 
//        j.addProperty("cityId", "520100"); 
//        j.addProperty("token", "ca3fd93703461e577b11a30b8a50ecb6"); 
      	
        //取消订单
//		j.addProperty("orderId", "4028b8815c26b59b015c26c92ae80010"); 
//		j.addProperty("token", ""); 
        
        //用户意见反馈
//		j.addProperty("userId", "4028b8815c26b59b015c26c92ae80010"); 
//		j.addProperty("token", "111"); 
//		j.addProperty("content", "测试用户反馈信息"); 
        
        //修改用户信息
//        j.addProperty("token", "test");
//        j.addProperty("header", strImg);
//        j.addProperty("userId", "4028820d5beb2c65015beb3fa78b0185");
//        j.addProperty("imgName", "中文名称.png");
//        j.addProperty("cardNumber", "420984199302021025");
//        j.addProperty("address", "软件园E1栋");
        
        //获取用户个人信息
//      j.addProperty("token", "test");
//      j.addProperty("userId", "4028820d5beb2c65015beb3fa78b0185");
        
        // 返回Base64编码过的字节数组字符串
//        System.out.println(j.toString());
//        System.out.println(Base64Util.getBase64(j.toString()));
        try {
			System.out.println(ac.post(Base64Util.getBase64(URLEncoder.encode(j.toString(), "UTF-8"))));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
        
//        System.out.println(ac.post("{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 456}}}"));
    }

    /** 
     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误 
     *  
     * @return the status 
     */  
    public int getStatus() {  
        return status;  
    }  
  
    /** 
     * @param status 
     *            the status to set 
     */  
    public void setStatus(int status) {  
        this.status = status;  
    }  
  
    /** 
     * @return the startTime 
     */  
    public long getStartTime() {  
        return startTime;  
    }  
  
    /** 
     * @return the endTime 
     */  
    public long getEndTime() {  
        return endTime;  
    }  
}  