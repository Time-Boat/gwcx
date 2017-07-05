package com.yhy.lin.app.wechat.menu;

/**
* Description : 微信自定义菜单，策略模式
* @author Administrator
* @date 2017年7月3日 下午4:57:24
*/
public abstract class Menu {
	
	abstract String execute(MenuContext mc);
	
}
