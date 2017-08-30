package com.yhy.lin.app.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
* Description : 
* @author Administrator
* @date 2017年8月30日 上午10:25:41
*/
public class AESUtil {
	/*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
//  private static String sKey = "WHNC001300000000";//key，可自行修改
    private static String ivParameter = "A%*/&9yJ3#>@!xC$";//偏移量,可自行修改
    public static void main(String[] args) throws Exception{
        //需要加密的字串
        String cSrc = "这只是用来测试AES加密和解密的字符串。";
        String sKey = "WHNC001300000000";//key，可自行修改
        //加密
        String enString = encrypt(cSrc,sKey);
        System.out.println("加密后的字串是：" + enString);
        //解密
        String DeString = decrypt(enString,sKey);
        System.out.println("解密后的字串是：" + DeString);
    }
    //加密
    public static String encrypt(String sSrc,String skey) throws Exception {
    	if(sSrc==null||"".equals(sSrc)){
    		return null;
    	}
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = skey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码
    }
    //解密
    public static String decrypt(String sSrc,String skey) throws Exception {
    	if(sSrc==null||"".equals(sSrc)){
    		return null;
    	}
    	byte[] raw = skey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
    }
}
