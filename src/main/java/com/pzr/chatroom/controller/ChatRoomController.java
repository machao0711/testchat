package com.pzr.chatroom.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.pzr.chatroom.entity.CacheBean;
import com.pzr.chatroom.entity.ChatContent;
import com.pzr.chatroom.entity.User;


/**
 * 演示控制器
 * @author pzr
 *
 */
@Controller
public class ChatRoomController {
	
	 @Autowired
	 private SimpMessagingTemplate template;
	
	/**
	 * 聊天
	 * @param msg
	 * @return
	 */
	@MessageMapping("/talk")//浏览器映射地址
	@SendTo("/refreshchatwindow")
	public ChatContent talk(ChatContent content) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		content.setDate(sdf.format(new Date()));
		return content;
	}
	
	/**
	 * 下线
	 * @param msg
	 * @return
	 */
	@MessageMapping("/downLine")
	private void downLine(User msg) {
		List<User> list = CacheBean.clientList;
		list.remove(msg);
		//服务器端通知客户端刷新当前登录人列表
		refreshLoginList();
	}

	/**
	 * 上线
	 * @param msg
	 * @return
	 */
	@MessageMapping("/upLine")
	private void upLine(User msg) {
		//内存中将用户加入进来
		List<User> list = CacheBean.clientList;
		list.add(msg);
		//服务器端通知客户端刷新当前登录人列表
		refreshLoginList();
	}
	
	/**
	 * 服务器端通知客户端刷新当前登录人列表
	 * @param list
	 */
	@MessageMapping("/refreshLoginList")
	public void refreshLoginList( ){
		template.convertAndSend("/refreshloginlist",CacheBean.clientList);
	}
}
