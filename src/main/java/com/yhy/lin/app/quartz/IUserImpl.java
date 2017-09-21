package com.yhy.lin.app.quartz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年9月20日 下午2:42:11
 */
@Component
public class IUserImpl implements IUser {

	public static Map map = null;

	public static void init() {
		String[] list = { "Lucy", "Tom", "小明", "Smith", "Hello" };
		Map tmp = new HashMap();
		for (int i = 0; i < list.length; i++) {
			tmp.put(list[i], list[i] + "00");
		}
		map = tmp;
	}

	public void addUser(String username) {
		init();
		map.put(username, username + "11");
		System.out.println("--------------【addUser】: " + username + " --------------");
		System.out.println("【The new List:" + map + "】");
	}

	public void findAll() {
		init();
		System.out.println("---------------【findAll】: " + map + " ------------------");
	}

	public String findUser(String username) {
		init();
		String password = "没查到该用户";
		if (map.containsKey(username)) {
			password = map.get(username).toString();
		}
		System.out.println("-----------------【findUser】-----------------");
		System.out.println("-----------------Username:" + username + "-----------------");
		System.out.println("-----------------【Result】:" + password + "------------------");
		return password;

	}
	
	@SuppressWarnings("resource")
	public static void main(String as[]) {
		BeanFactory factory = new ClassPathXmlApplicationContext("spring-mvc-aop.xml");
		IUser user = (IUser) factory.getBean("IUserImpl");
		user.findAll();

		//User u = new User();
		//u.setUsername("Tom");
		//user.findUser(u.getUsername());

		/*
		 * u.setUsername("haha"); user.addUser(u.getUsername());
		 */
	}

}
