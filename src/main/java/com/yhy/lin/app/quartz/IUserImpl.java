package com.yhy.lin.app.quartz;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年9月20日 下午2:42:11
 */
@Aspect
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
//		BeanFactory factory = new ClassPathXmlApplicationContext("spring-mvc-aop.xml");
//		IUser user = (IUser) factory.getBean("IUserImpl");
//		user.findAll();
//		System.out.println();
//		user.findUser("Tom");
		//User u = new User();
		//u.setUsername("Tom");
		//user.findUser(u.getUsername());

		/*
		 * u.setUsername("haha"); user.addUser(u.getUsername());
		 */
		
//		Factory.createApi(1).test();
		
		ImplB b = new IUserImpl().new ImplB();
		ImplB a = new IUserImpl().new ImplB();
		Map<String,Object> m = new HashMap<>();
		Map<String,Object> m1 = new HashMap<>();
		m.put("a", a);
		m1.put("a", b);
		System.out.println(m.equals(m1));
	}

	interface C{
		void test();
	}
	
	class ImplA implements C{

		@Override
		public void test() {
			System.out.println("ImplA");
		}
		
	}
	
	class ImplB implements C{
		
		@Override
		public void test() {
			System.out.println("ImplB");
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof ImplB){
				return true;
			}
			return false;
		}
		
	}
	
	static class Factory{
		public static C createApi(int type){
			switch (type) {
				case 0:
					return new IUserImpl().new ImplA();
				case 1:
					return new IUserImpl().new ImplB();
				default:
					return null;
			}
			
		}
	}
	
}
