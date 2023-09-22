package com.luxfacta.vernyy.api.push;

public class Notification {

	private String title;
	private String message;
	private String icon;
	private String target;
	
	
	
	public Notification(String title, String message, String target) {
		this.title = title;
		this.message = message;
		this.target = target;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	
	
}
