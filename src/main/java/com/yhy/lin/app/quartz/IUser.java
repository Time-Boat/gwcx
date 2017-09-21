package com.yhy.lin.app.quartz;

/**
* Description : 
* @author Administrator
* @date 2017年9月20日 下午2:41:58
*/
public interface IUser {  
	  
    public String findUser(String username);  
    public void addUser(String username);  
    public void findAll();  
}  
