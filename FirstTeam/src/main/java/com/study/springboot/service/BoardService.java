package com.study.springboot.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.study.springboot.dao.IBoardDao;
import com.study.springboot.dao.IClassLikeManagerDao;
import com.study.springboot.dao.IClassManagerDao;
import com.study.springboot.dto.BoardDto;
import com.study.springboot.dto.ClassManagerDto;
@Service
public class BoardService implements IBoardService{

	@Autowired
	IBoardDao boardDao;
	@Autowired
	IClassManagerDao classDao;
	@Autowired
	IClassLikeManagerDao classlikeDao;
	
	@Override
	public int countJoinClass() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
        
		int joinclass=classDao.countJoinClassDao(userid);
		return joinclass;
	}
	@Override
	public ModelAndView AllBoardInfo(){
		
		ModelAndView mv=new ModelAndView();
//		System.out.println(boardDao.AllBoardInfoDao());
		ArrayList<BoardDto> bl=boardDao.AllBoardInfoDao();
		for(BoardDto bd:bl) {
			if(bd.getTfile()==null) {
				System.out.println("AllBoardInfo Service: tfile null");
				bd.setTfile("not-available.png");
			}
		}
		mv.addObject("rblist", bl);
		
		return mv;
	}
	@Override
	public ModelAndView viewClassInfo(String tid){
		System.out.println("BS > viewClassInfo");
		int classid=Integer.parseInt(tid);
		
		ModelAndView mv=new ModelAndView();
		BoardDto dto=new BoardDto();
		dto=boardDao.viewClassInfoDao(classid);
		//????????? ?????? ???????????? ?????????
		int memnum=dto.getTmemnum();
		int nownum=dto.getTnownum();
//		System.out.println("memnum ; "+memnum+", nownum : "+nownum);
		
		int availnum=memnum-nownum;
		System.out.println("??????????????? ????????? : "+ availnum);
		
		//????????? ?????? ?????? list ????????????		???
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
//        System.out.println("session userid: "+userid);
        
        Map<String, Object> map=new HashMap<String, Object>();
		map.put("classid", classid);
		map.put("userid", userid);
		int checkuser=classDao.getClassMemberDao(map);
		System.out.println("checkuser: "+checkuser +", session userid : "+userid);
		
		//???????????? ????????????		
		ArrayList<ClassManagerDto> list = classDao.getClassInfo(classid);
		
		//lat, lon????????????
		Double minLat = 500000.0;
		for(int i = 0; i < list.size(); i++)
		{
			Double lat = Double.parseDouble(list.get(i).getLat());
			if(lat<minLat)
				minLat = lat;
						
		}
//		System.out.println(minLat);
		
		Double maxLat = 0.0;
		for(int i = 0; i < list.size(); i++)
		{
			Double lon = Double.parseDouble(list.get(i).getLat());
			if(lon>maxLat)
				maxLat= lon;
						
		}
//		System.out.println(maxLat);
		
		Double minLon = 500000.0;
		for(int i = 0; i < list.size(); i++)
		{
			Double lat = Double.parseDouble(list.get(i).getLon());
			if(lat<minLon)
				minLon = lat;
						
		}
//		System.out.println(minLon);
		
		Double maxLon = 0.0;
		for(int i = 0; i < list.size(); i++)
		{
			Double lon = Double.parseDouble(list.get(i).getLon());
			if(lon>maxLon)
				maxLon= lon;
						
		}
//		System.out.println(maxLon);
		
		Double centerX = minLat + ((maxLat - minLat) / 2);
		Double centerY = minLon + ((maxLon - minLon) / 2);
		
//		System.out.println(centerX + ", " + centerY);
		
		//????????? ??? ??????
		int likecount=classlikeDao.likeCountDao(classid);
		//userid ????????? ?????? ??????
		int likecheck=classlikeDao.likeCheckDao(classid, userid);
		System.out.println(dto.getClassstartdate().toString().substring(0,11).replace("-", "/")+","+dto.getClassenddate().toString().substring(0,11).replace("-", "/"));
		System.out.println("====================================viewClassInfo=======================================");
		
		mv.addObject("classinfo", dto);
		mv.addObject("availnum", availnum);
		mv.addObject("checkuser", checkuser);
		mv.addObject("list", list);
		mv.addObject("lat", centerX);
		mv.addObject("lon", centerY);
		mv.addObject("likecount", likecount);
		mv.addObject("likecheck", likecheck);
		mv.setViewName("view/contentview");
		
		return mv;
	}
	@Override
	public int meetUpWrite(HttpServletRequest request) {
		System.out.println("Service: meetupWrite");
		int nResult=0;
		//??????????????? ?????? ??????
		int size=1024*1024*10;
		String file="";
		String orifile="";
		//???????????? session id??? ????????????
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        

		try {
			String basePath=ResourceUtils.getFile("classpath:static/classimages/").toPath().toString();
			MultipartRequest multi=new MultipartRequest(request ,basePath,size, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration files=multi.getFileNames();
			String str=(String)files.nextElement();
			
			file=multi.getFilesystemName(str);
			orifile=multi.getOriginalFileName(str);
			
			System.out.println("file: "+file+", orifile: "+orifile);
			
			String tUserID = userDetails.getUsername();;
			String tCategory = multi.getParameter("tCategory");
			String mName = multi.getParameter("mName");
			String mTel = multi.getParameter("mTel");
			String mEmail = multi.getParameter("mEmail");
			String title = multi.getParameter("title");
			String tIntro = multi.getParameter("tIntro");
			String tContent = multi.getParameter("tContent");
			String tFile = file;
			String classStartDate = multi.getParameter("classStartDate");
			String classEndDate = multi.getParameter("classEndDate");
			String regStartDate = multi.getParameter("regStartDate");
			String regEndDate = multi.getParameter("regEndDate");
			String tSpaceType = multi.getParameter("tSpaceType");
			String tSpace = multi.getParameter("tSpace");
			String tSpaceDetail=multi.getParameter("tSpaceDetail");
			String tMemnum = multi.getParameter("tMemnum");
			String tPayment = multi.getParameter("payment");
			String tPrice = multi.getParameter("price");
			
			if(tSpaceType.equals("online")) {
				tSpace = "zoom";
			}else if (tSpaceType.equals("locnon")) {
				tSpace = "?????? ??????";
			}
			else if (tSpaceType.equals("locfix")) {
				tSpace = tSpace+" / "+tSpaceDetail;
				System.out.println("???????????? concat: "+tSpace);
			}


			System.out.println("tMemnum "+tMemnum+"tPayment: "+tPayment+"tPrice: "+tPrice);

			System.out.println(tUserID + " / " + tCategory + " / " + mName + " / " + mTel + " / " + mEmail + " / " + title
			+ " / " + tIntro + " / " + tContent + " / " + tFile + " / " + classStartDate + " / " + classEndDate
			+ " / " + regStartDate + " / " + regEndDate + " / " + tSpaceType + " / " + tSpace + " / "
			+ Integer.parseInt(tMemnum) + " / " + tPayment + " / " + Integer.parseInt(tPrice));
			
			nResult=boardDao.meetupwrite(tUserID, tCategory, mName, mTel, mEmail, title, tIntro, tContent, tFile, classStartDate,
					classEndDate, regStartDate, regEndDate, tSpaceType, tSpace, Integer.parseInt(tMemnum), tPayment, Integer.parseInt(tPrice));
			System.out.println("????????? ????????? insert ?????? nResult: "+nResult);
			
		}catch(Exception e) {
			e.printStackTrace();
		}	

		return nResult;
	}
	@Override
	public int modWrite(HttpServletRequest request) {
		System.out.println("Service: modWrite");
		int nResult=0;
		//??????????????? ?????? ??????
		int size=1024*1024*10;
		String file="";
		String orifile="";
		//???????????? session id??? ????????????
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        

		try {
			String basePath=ResourceUtils.getFile("classpath:static/classimages/").toPath().toString();
			MultipartRequest multi=new MultipartRequest(request ,basePath,size, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration files=multi.getFileNames();
			System.out.println("enumeration files: "+files);
			String str=(String)files.nextElement();
			
			file=multi.getFilesystemName(str);
			orifile=multi.getOriginalFileName(str);
			
			System.out.println("file: "+file+", orifile: "+orifile);
			
			String tid=multi.getParameter("tid");
			int classid=Integer.parseInt(tid);
			String tUserID = userDetails.getUsername();
			String tCategory = multi.getParameter("tCategory");
			String mName = multi.getParameter("mName");
			String mTel = multi.getParameter("mTel");
			String mEmail = multi.getParameter("mEmail");
			String title = multi.getParameter("title");
			String tIntro = multi.getParameter("tIntro");
			String tContent = multi.getParameter("tContent");
			String tFile = file;
			String classStartDate = multi.getParameter("classStartDate");
			String classEndDate = multi.getParameter("classEndDate");
			String regStartDate = multi.getParameter("regStartDate");
			String regEndDate = multi.getParameter("regEndDate");
			String tSpaceType = multi.getParameter("tSpaceType");
			String tSpace = multi.getParameter("tSpace");
			String tSpaceDetail=multi.getParameter("tSpaceDetail");
			String tMemnum = multi.getParameter("tMemnum");
			String tPayment = multi.getParameter("payment");
			String tPrice = multi.getParameter("price");
			
			if(tSpaceType.equals("online")) {
				tSpace = "zoom";
			}else if (tSpaceType.equals("locrecommand")) {
				tSpace = "?????? ??????";
			}
			else if (tSpaceType.equals("locfix")) {
				tSpace = tSpace+" / "+tSpaceDetail;
				System.out.println("???????????? concat: "+tSpace);
			}
			System.out.println("tMemnum "+tMemnum+"tPayment: "+tPayment+"tPrice: "+tPrice);
			//file ???????????? ????????? ??? file ??? ????????? ??? ??????
			if(tFile==null) {
				nResult=boardDao.modnfwriteDao(tUserID, tCategory, mName, mTel, mEmail, title, tIntro, tContent, classStartDate,
						classEndDate, regStartDate, regEndDate, tSpaceType, tSpace, Integer.parseInt(tMemnum), tPayment, Integer.parseInt(tPrice), classid);
			}else {
				System.out.println(tUserID + " / " + tCategory + " / " + mName + " / " + mTel + " / " + mEmail + " / " + title
						+ " / " + tIntro + " / " + tContent + " / " + tFile + " / " + classStartDate + " / " + classEndDate
						+ " / " + regStartDate + " / " + regEndDate + " / " + tSpaceType + " / " + tSpace + " / "
						+ Integer.parseInt(tMemnum) + " / " + tPayment + " / " + Integer.parseInt(tPrice));
				nResult=boardDao.modwriteDao(tUserID, tCategory, mName, mTel, mEmail, title, tIntro, tContent, tFile, classStartDate,
					classEndDate, regStartDate, regEndDate, tSpaceType, tSpace, Integer.parseInt(tMemnum), tPayment, Integer.parseInt(tPrice), classid);

			}
						System.out.println("????????? ????????? update?????? nResult: "+nResult);
			
		}catch(Exception e) {
			e.printStackTrace();
		}	

		return nResult;
	}
	@Override
	public int fixaddress(String tid, String address) {
		boardDao.fixaddress(Integer.parseInt(tid), address);
		return 0;
	}
	

	
}
