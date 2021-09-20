package com.study.springboot.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.study.springboot.dao.IBoardDao;
import com.study.springboot.dao.IClassLikeManagerDao;
import com.study.springboot.dao.IClassManagerDao;
import com.study.springboot.dao.IUserDao;
import com.study.springboot.dto.BoardDto;
import com.study.springboot.dto.ClassLikeManagerDto;
import com.study.springboot.dto.UserDto;

@Service
public class MypageService implements IMypageService{
	@Autowired
	IUserDao userdao;
	@Autowired
	IBoardDao boarddao;
	@Autowired
	IClassManagerDao classdao;
	@Autowired
	IClassLikeManagerDao classlikedao;
	
	public ModelAndView goMypage() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
        
		ModelAndView mv=new ModelAndView();
		List<UserDto> usi=userdao.getUserInfo(userid);
		//핸드폰 번호 형식 
		String userphone="";
		if(usi.get(0).getTxtMobile1()!=null) {
			userphone=usi.get(0).getTxtMobile1()+"-"+usi.get(0).getTxtMobile2()+"-"+usi.get(0).getTxtMobile3();
			System.out.println("txtMobile concat: "+userphone);
		}else {
			userphone="등록된 번호가 없습니다.";
		}
		//성별 출력
		if(usi.get(0).getUsergender()==null) {			
			usi.get(0).setUsergender("성별을 입력하지 않았습니다.");
			System.out.println("usergender null: "+usi.get(0).getUsergender());
		}else if(usi.get(0).getUsergender().equals("woman")) {
			System.out.println("usergender woman: "+usi.get(0).getUsergender());
			usi.get(0).setUsergender("여성");
		}else if(usi.get(0).getUsergender().equals("man")){
			System.out.println("usergender man: "+usi.get(0).getUsergender());
			usi.get(0).setUsergender("남성");
		}
		//프로필 사진 없을시
		if(usi.get(0).getUserprofile()==null) {
			System.out.println("Service getuserprofile: null");
			usi.get(0).setUserprofile("not-available.png");
		}
		//회원정보조회
		mv.addObject("userinfo", usi);	
		mv.addObject("userphone", userphone);
		//신청한 클래스 개수
		mv.addObject("joinclass", classdao.countJoinClassDao(userid));
		//좋아요한 클래스 개수
		mv.addObject("likecount", classlikedao.userLikeCountCheckDao(userid));
		//개설한 클래스 개수
		mv.addObject("openclass", boarddao.countOpenClassDao(userid));
		
		mv.setViewName("mypage/mypage");
		
		return mv;
	}
	public ModelAndView getLikeClassInfo(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
		
		ModelAndView mv=new ModelAndView();
		
		List<ClassLikeManagerDto> list=new ArrayList<>();
		list=classlikedao.getLikeClassInfoDao(userid);
		System.out.println("좋아요 list: "+list);
		System.out.println("좋아요 list.size() : "+ list.size());
		
		List<BoardDto> classinfo=new ArrayList<BoardDto>();
		
		for(int i=0; i<list.size();i++ ) {
			classinfo.add(boarddao.getClassInfoDao(list.get(i).getClassid()));
		}
		
		mv.addObject("likeclassinfo",classinfo);
		return mv;	
	}
	public ModelAndView getOpenClassInfo(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
		
		ModelAndView mv=new ModelAndView();

		mv.addObject("openclassinfo",boarddao.getOpenClassInfoDao(userid));
		return mv;	
	}
	
	public ModelAndView doDeleteClass(String tid) {
		int classid=Integer.parseInt(tid);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("classinfo", boarddao.getClassInfoDao(classid));
		mv.addObject("memberinfo", classdao.getApplyMemberDao(classid));
		mv.addObject("countmem", classdao.countNowmemDao(classid));

		return mv;		
	}
	public ModelAndView modifyClass(String tid) {
		System.out.println("Service: modifyClass");
		int classid=Integer.parseInt(tid);
		
		ModelAndView mv=new ModelAndView();
		BoardDto dto=boarddao.getClassInfoDao(classid);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		mv.addObject("classstartdate",dto.getClassstartdate());
		mv.addObject("classenddate",dto.getClassenddate());
		mv.addObject("regstartdate",dto.getRegstartdate());
		mv.addObject("regenddate",dto.getRegenddate());
		mv.addObject("classinfo", dto);
		
		return mv;
	}
}
