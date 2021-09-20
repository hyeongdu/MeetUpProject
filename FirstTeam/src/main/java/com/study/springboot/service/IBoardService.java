/**
 * 
 */
package com.study.springboot.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface IBoardService {
	public int countJoinClass();
	ModelAndView AllBoardInfo();
	ModelAndView viewClassInfo(String tid);
//	public int meetUpWrite(String tUserID, String tCategory, String mName, String mTel, String mEmail, String title,
//			String tIntro, String tContent, String tFile, String classStartDate, String classEndDate,
//			String regStartDate, String regEndDate, String tSpaceType, String tSpace, String tMemnum, String tPayment, String tPrice);
	public int meetUpWrite(HttpServletRequest request);
	public int modWrite(HttpServletRequest request);
	public int fixaddress(String tid, String address);

}
