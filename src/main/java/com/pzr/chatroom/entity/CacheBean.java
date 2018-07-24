package com.pzr.chatroom.entity;


import java.util.ArrayList;
import java.util.List;

public class CacheBean {
	/**
	 * 使用静态集合来存储当前登录用户
	 */
	public static List<User> clientList = new ArrayList<User>();
}
