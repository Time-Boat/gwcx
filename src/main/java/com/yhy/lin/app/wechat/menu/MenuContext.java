package com.yhy.lin.app.wechat.menu;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年7月3日 下午5:33:10
 */
public class MenuContext {

	private final String BUY_TICKET_MENU = "rselfmenu_0_0";

	private final String TICKET_POLICY = "rselfmenu_0_1";

	/** 菜单唯一标识 */
	private String key;
	/** 菜单类型*/
	private String type;
	/** 用户唯一标识 */
	private String openId;
	/** 菜单 */
	private Menu menu;

	public String getKey() {
		return key;
	}

	public String getType() {
		return type;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setMenu(String key) {
		Menu menu = null;
		switch (key) {
		case BUY_TICKET_MENU:
			menu = new BuyTicket();
			break;
		case TICKET_POLICY:
			menu = new TicketPolicy();
			break;
		default:
			menu = new BuyTicket();
			break;
		}
		this.menu = menu;
	}

	public MenuContext(String key, String type, String openId) {
		this.key = key;
		this.type = type;
		this.openId = openId;
		setMenu(key);
	}

	/** 执行 */
	public String execute() {
		return menu.execute(this);
	}

}
