package com.study.springboot.service;

import org.springframework.web.servlet.ModelAndView;

public interface IMypageService {
	ModelAndView goMypage();
	ModelAndView getLikeClassInfo();
	ModelAndView getOpenClassInfo();
	ModelAndView doDeleteClass(String tid);
	ModelAndView modifyClass(String tid);
}
