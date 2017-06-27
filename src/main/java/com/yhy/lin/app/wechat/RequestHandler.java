package com.yhy.lin.app.wechat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.MD5Util;

/*
 '微信支付服务器签名支付请求请求类
 '============================================================================
 'api说明：
 'init(app_id, app_secret, partner_key, app_key);
 '初始化函数，默认给一些参数赋值，如cmdno,date等。
 'setKey(key_)'设置商户密钥
 'getLasterrCode(),获取最后错误号
 'GetToken();获取Token
 'getTokenReal();Token过期后实时获取Token
 'createMd5Sign(signParams);生成Md5签名
 'genPackage(packageParams);获取package包
 'createSHA1Sign(signParams);创建签名SHA1
 'sendPrepay(packageParams);提交预支付
 'getDebugInfo(),获取debug信息
 '============================================================================
 '*/
public class RequestHandler {
	/** Token获取网关地址地址 */
	private String tokenUrl;
	/** 预支付网关url地址 */
	private String gateUrl;
	/** 查询支付通知网关URL */
	private String notifyUrl;
	/** 商户参数 */
	private String appid;
	private String appkey;
	private String partnerkey;
	private String appsecret;
	private String key;
	/** 请求的参数 */
	private SortedMap parameters;
	/** Token */
	private String Token;
	private String charset;
	/** debug信息 */
	private String debugInfo;
	private String last_errcode;

	private HttpServletRequest request;

	private HttpServletResponse response;

	/**
	 * 初始构造函数。
	 * 
	 * @return
	 */
	public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.last_errcode = "0";
		this.request = request;
		this.response = response;
		// this.charset = "GBK";
		this.charset = "UTF-8";
		this.parameters = new TreeMap();
		// 验证notify支付订单网关
		notifyUrl = "https://gw.tenpay.com/gateway/simpleverifynotifyid.xml";

	}

	/**
	 * 初始化函数。
	 */
	public void init(String app_id, String app_secret, String partner_key) {
		this.last_errcode = "0";
		this.Token = "token_";
		this.debugInfo = "";
		this.appid = app_id;
		this.partnerkey = partner_key;
		this.appsecret = app_secret;
		this.key = partner_key;
	}

	public void init() {
	}

	/**
	 * 获取最后错误号
	 */
	public String getLasterrCode() {
		return last_errcode;
	}

	/**
	 * 获取入口地址,不包含参数值
	 */
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	// 设置密钥

	public void setKey(String key) {
		this.partnerkey = key;
	}

	// 设置微信密钥
	public void setAppKey(String key) {
		this.appkey = key;
	}

	// 特殊字符处理
	public String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, this.charset).replace("+", "%20");
	}

	// 获取package的签名包
	public String genPackage(SortedMap<String, String> packageParams) throws UnsupportedEncodingException {
		String sign = createSign(packageParams);

		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + UrlEncode(v) + "&");
		}

		// 去掉最后一个&
		String packageValue = sb.append("sign=" + sign).toString();
		// System.out.println("UrlEncode后 packageValue=" + packageValue);
		return packageValue;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		System.out.println("md5 sb:" + sb);
		String sign = MD5Util.AppMD5(sb.toString()).toUpperCase();
		System.out.println("packge签名:" + sign);
		return sign;

	}

	/**
	 * 创建package签名
	 */
	public boolean createMd5Sign(String signParams) {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		// 算出摘要
		// String enc = TenpayUtil.getCharacterEncoding(this.request,
		// this.response);
		String sign = MD5Util.MD5(sb.toString()).toLowerCase();

		String tenpaySign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:" + tenpaySign);

		return tenpaySign.equals(sign);
	}

	// 输出XML
	public String parseXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {

				sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public String getKey() {
		return partnerkey;
	}
	
	
	 //连接超时时间，默认10秒  
    private int socketTimeout = 10000;  
  
    //传输超时时间，默认30秒  
    private int connectTimeout = 30000;  
  
    //请求器的配置  
    private RequestConfig requestConfig;  
  
    //HTTP请求器  
    private CloseableHttpClient httpClient;  
  
    /** 
     * 加载证书 
     * @param path 
     * @throws IOException 
     * @throws KeyStoreException 
     * @throws UnrecoverableKeyException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */  
    private void initCert(String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {  
        //拼接证书的路径  
        path = path + AppGlobals.CERT_PATH_LOCATION;  
//        path = AppGlobals.CERT_PATH_LOCATION;  
        KeyStore keyStore = KeyStore.getInstance("PKCS12");  
  
        //加载本地的证书进行https加密传输  
        FileInputStream instream = new FileInputStream(new File(path));  
        try {  
            keyStore.load(instream, AppGlobals.MCH_ID.toCharArray());  //加载证书密码，默认为商户ID  
        } catch (CertificateException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } finally {  
            instream.close();  
        }  
  
        // Trust own CA and all self-signed certs  
        SSLContext sslcontext = SSLContexts.custom()  
                .loadKeyMaterial(keyStore, AppGlobals.MCH_ID.toCharArray())       //加载证书密码，默认为商户ID  
                .build();  
        // Allow TLSv1 protocol only  
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                sslcontext,  
                new String[]{"TLSv1"},  
                null,  
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
  
        httpClient = HttpClients.custom()  
                .setSSLSocketFactory(sslsf)  
                .build();  
        
        //根据默认超时限制初始化requestConfig  
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();  
        
    }  
  
  
    /** 
     * 通过Https往API post xml数据 
     * @param url   API地址 
     * @param xmlObj   要提交的XML数据对象 
     * @param path    当前目录，用于加载证书 
     * @return 
     * @throws IOException 
     * @throws KeyStoreException 
     * @throws UnrecoverableKeyException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */  
    public String httpsRequest(String url, String xmlObj, String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {  
        //加载证书  
        initCert(path);  
  
        String result = null;  
  
        HttpPost httpPost = new HttpPost(url);  
  
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别  
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");  
        httpPost.addHeader("Content-Type", "text/xml");  
        httpPost.setEntity(postEntity);  
  
        //设置请求器的配置  
        httpPost.setConfig(requestConfig);  
  
        try {  
            HttpResponse response = httpClient.execute(httpPost);  
  
            HttpEntity entity = response.getEntity();  
  
            result = EntityUtils.toString(entity, "UTF-8");  
  
        } catch (ConnectionPoolTimeoutException e) {  
            e.printStackTrace();
//        	LogUtils.trace("http get throw ConnectionPoolTimeoutException(wait time out)");  
  
        } catch (ConnectTimeoutException e) {  
//            LogUtils.trace("http get throw ConnectTimeoutException");  
            e.printStackTrace();
        } catch (SocketTimeoutException e) {  
//             LogUtils.trace("http get throw SocketTimeoutException");  
             e.printStackTrace();
        } catch (Exception e) {  
//             LogUtils.trace("http get throw Exception");  
             e.printStackTrace();
        } finally {  
            httpPost.abort();  
            httpClient.close();
        }  
  
        return result;  
    }  

}
