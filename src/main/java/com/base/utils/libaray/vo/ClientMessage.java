package com.base.utils.libaray.vo;

import java.io.Serializable;

public class ClientMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String key;
	private Long userId;
	private String userName;
	private String message;
	private Boolean closeWindow;
	private String sessionId;
	
	
	
	public ClientMessage() {
	}


	public ClientMessage(String key, Long userId, String message, Boolean closeWindow) {
		super();
		this.key = key;
		this.userId = userId;
		this.message = message;
		this.closeWindow = closeWindow;
	}
	
	
	public ClientMessage(String key, String userName, String message, Boolean closeWindow) {
		super();
		this.key = key;
		this.userName = userName;
		this.message = message;
		this.closeWindow = closeWindow;
	}


	public ClientMessage(String key, String message, Boolean closeWindow, String sessionId) {
		super();
		this.key = key;
		this.message = message;
		this.closeWindow = closeWindow;
		this.sessionId = sessionId;
	}


	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getCloseWindow() {
		return closeWindow;
	}
	public void setCloseWindow(Boolean closeWindow) {
		this.closeWindow = closeWindow;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
