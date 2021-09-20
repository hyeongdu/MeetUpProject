package com.study.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.study.springboot.dao.IBoardDao;
import com.study.springboot.dao.IClassManagerDao;
import com.study.springboot.dao.IPaymentInfoDao;
import com.study.springboot.dao.IUserDao;
import com.study.springboot.dto.BoardDto;

@Service
public class PaymentService implements IPaymentService {
	@Autowired
	IBoardDao boardDao;
	@Autowired
	IUserDao userDao;
	@Autowired
	IClassManagerDao cmDao;
	@Autowired
	IPaymentInfoDao payDao;
	
	@Override
	public ModelAndView getClassInfo(String tid){
		
		int classid=Integer.parseInt(tid);
		
		ModelAndView mv=new ModelAndView();
		BoardDto dto=new BoardDto();
		dto=boardDao.viewClassInfoDao(classid);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
        System.out.println("session userid: "+userid);
        
		mv.addObject("classinfo", dto);
		mv.addObject("userinfo", userDao.getUserInfo(userid));
		mv.setViewName("view/paymentpage");
		System.out.println(mv);
		
		return mv;
	}
	@Override
	public int insertPayInfoDb(String userid, String tid, String merchant_id) {
		int classid=Integer.parseInt(tid);
		
		int nResult=payDao.insertPaymentDao(userid, classid, merchant_id);
		System.out.println("paymentInfoBoard에 결제정보 저장 : "+nResult);
		int nResult2=cmDao.updatePaycheck(classid, userid);
		System.out.println("Classmanager에 payment update : "+nResult2);
		
		return nResult+nResult2;
	}
	@Override
	public int checkPay(String userid, String tid) {
		int classid=Integer.parseInt(tid);
		
		int nResult=payDao.checkPayDao(userid, classid);
		System.out.println("결제여부 결과 : "+nResult);
		
		return nResult;
	}
}
