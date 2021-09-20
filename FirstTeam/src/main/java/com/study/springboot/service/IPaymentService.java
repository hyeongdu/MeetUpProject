package com.study.springboot.service;

import org.springframework.web.servlet.ModelAndView;

public interface IPaymentService {
	ModelAndView getClassInfo(String tid);
	public int insertPayInfoDb(String userid, String tid, String merchant_id);
	public int checkPay(String userid, String tid);
}
