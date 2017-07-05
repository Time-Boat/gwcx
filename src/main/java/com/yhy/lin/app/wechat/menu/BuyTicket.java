package com.yhy.lin.app.wechat.menu;

/**
* Description : 订票
* @author Administrator
* @date 2017年7月3日 下午5:06:48
*/
public class BuyTicket extends Menu{
	
	private String ticketUrl = "http://car.cywtrip.com/job/index.html";
	
	@Override
	String execute(MenuContext mc) {
		String url = ticketUrl + "&" + mc.getOpenId();
		return url;
	}
	
}
