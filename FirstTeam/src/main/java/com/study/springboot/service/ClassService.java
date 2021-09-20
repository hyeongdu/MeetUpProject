package com.study.springboot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.study.springboot.dao.IBoardDao;
import com.study.springboot.dao.IClassLikeManagerDao;
import com.study.springboot.dao.IClassManagerDao;
import com.study.springboot.dto.BoardDto;
import com.study.springboot.dto.ClassManagerDto;

@Service
public class ClassService implements IClassService{
	@Autowired
	IClassManagerDao cd;
	@Autowired
	IBoardDao bd;
	@Autowired
	IClassLikeManagerDao cld;

    
	public int applyClass(String tid, String title, String lat, String lon, String address) {
		    
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;    
        String userid = userDetails.getUsername();
        
		int Itid=Integer.parseInt(tid);
		
		System.out.println("tid : "+tid+",title: "+title+"lat: "+lat+"lon : "+lon+",address: "+address);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tid", Itid);
		map.put("title", title);
		map.put("userid", userid);
		if(lat==null) {
			map.put("lat", "0");
		}else {
			map.put("lat", lat);
		}
		
		if(lon==null) {
			map.put("lon", "0");
		}else {
			map.put("lon", lon);
		}
		if(address==null) {
			map.put("address", "none");
		}else {
			map.put("address", address);
		}
		
		map.put("paycheck", "n");
		int nResult=cd.applyClassDao(map);
		
		System.out.println("insert nResult : "+nResult);
		if(nResult==1) {
			int result=bd.addMemnumDao(Itid);
			System.out.println("update board result: "+result);
		}

		return nResult;
	}
	public ModelAndView getApplyClassInfo(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
		
		ModelAndView mv=new ModelAndView();
		
		List<ClassManagerDto> list=new ArrayList<>();
		list=cd.getApplyClassInfoDao(userid);
		System.out.println("classmanager list: "+list);
		System.out.println("list.size() : "+ list.size());
		mv.addObject("classmanagerinfo", list);
		
		List<BoardDto> classinfo=new ArrayList<BoardDto>();
		
		for(int i=0; i<list.size();i++ ) {
			classinfo.add(bd.getClassInfoDao(list.get(i).getClassid()));
		}
		
		mv.addObject("applyclassinfo",classinfo);
		return mv;	
	}
	public String quitClass(String userid, String classid) {
		int tid=Integer.parseInt(classid);
		
		String json="{\"code\":\"fail\"}";
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("tid", tid);
		
		int nResult=cd.quitClassDao(map);
		if(nResult==1) {
			System.out.println("nResult : "+nResult);
			int deleteResult=bd.deleteMemnumDao(tid);
			if(deleteResult==1) {
				json="{\"code\":\"success\"}";
			}
		}
		return json;
	}
	
	public int doLike(String tid, String classtitle) {
		int classid=Integer.parseInt(tid);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
        
        int nResult=cld.doLikeDao(classid, classtitle, userid);
        System.out.println("dolike nResult: "+nResult);
        
        return nResult;
		
	}
	public int undoLike(String tid) {
		int classid=Integer.parseInt(tid);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
		
        int nResult=cld.undoLikeDao(classid, userid);
        System.out.println("좋아요 취소 result: "+nResult);
        
        return nResult;
	}
	
	@Override
	public String locationRecommand(String tid) {
		System.out.println(tid);
		
		
		List<ClassManagerDto> dto = cd.locationList(Integer.parseInt(tid));
		
		Double minLat = 500000.0;
		for(int i = 0; i < dto.size(); i++)
		{
			Double lat = Double.parseDouble(dto.get(i).getLat());
			if(lat<minLat)
				minLat = lat;
						
		}
		System.out.println(minLat);
		
		Double maxLat = 0.0;
		for(int i = 0; i < dto.size(); i++)
		{
			Double lon = Double.parseDouble(dto.get(i).getLat());
			if(lon>maxLat)
				maxLat= lon;
						
		}
		System.out.println(maxLat);
		
		Double minLon = 500000.0;
		for(int i = 0; i < dto.size(); i++)
		{
			Double lat = Double.parseDouble(dto.get(i).getLon());
			if(lat<minLon)
				minLon = lat;
						
		}
		System.out.println(minLon);
		
		Double maxLon = 0.0;
		for(int i = 0; i < dto.size(); i++)
		{
			Double lon = Double.parseDouble(dto.get(i).getLon());
			if(lon>maxLon)
				maxLon= lon;
						
		}
		System.out.println(maxLon);
		
		Double centerX = minLat + ((maxLat - minLat) / 2);
		Double centerY = minLon + ((maxLon - minLon) / 2);
		
		System.out.println(centerX + ", " + centerY);
	
		
		String json = "{\"lat\":\""+centerX+"\",\"lon\":\""+centerY+"\"}";
		
		return json;
	}
	//클래스 매니저 이름을 가져옴
	public String checkManId(String classid) {
		int tid=Integer.parseInt(classid);
		
		String manId=bd.checkManIdDao(tid);
		
		return manId;
	}
	
	

}
