package com.study.springboot.service;

import java.util.ArrayList;

import org.springframework.web.servlet.ModelAndView;

import com.study.springboot.dto.ClassManagerDto;

public interface IClassService {
	public int applyClass(String tid, String title, String lat, String lon, String address);
	ModelAndView getApplyClassInfo();
	public String quitClass(String userid, String classid);
	public int doLike(String tid, String classtitle);
	public int undoLike(String tid);
	public String locationRecommand(String tid);
	public String checkManId(String classid);

}
