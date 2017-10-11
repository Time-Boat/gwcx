package com.yhy.lin.app.wechat.menu;

import com.yhy.lin.app.util.AppGlobals;

/**
* Description : 购票须知菜单按钮
* @author Administrator
* @date 2017年7月3日 下午5:28:43
*/
public class TicketPolicy extends Menu{
	
	private String ticketUrl = "http://" + AppGlobals.SERVER_BASE_URL + "/job/index.html";
	
	@Override
	String execute(MenuContext mc) {
		String url = ticketUrl + "&" + mc.getOpenId();
		return url;
	}

}
