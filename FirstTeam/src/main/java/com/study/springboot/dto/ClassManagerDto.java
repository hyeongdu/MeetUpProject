package com.study.springboot.dto;

import lombok.Data;

@Data
public class ClassManagerDto {
	private int classid;
	private String classname;
	private String userid;
	private String lat;
	private String lon;
	private String address;
	private String paycheck;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPaycheck() {
		return paycheck;
	}
	public void setPaycheck(String paycheck) {
		this.paycheck = paycheck;
	}
	
	
}
