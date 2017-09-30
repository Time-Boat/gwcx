package com.yhy.lin.app.util;

import javax.servlet.http.HttpServletRequest;

/**
* Description : http工具类
* @author Administrator
* @date 2017年9月29日 下午2:29:17
*/
public class HttpUtils {
    private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

    /**
     * 判断是什么浏览器 
     * */
    public static boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal))
                return true;
        }
        return false;
    }
}