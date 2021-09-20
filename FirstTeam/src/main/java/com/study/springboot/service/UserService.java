package com.study.springboot.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.springboot.dao.IUserDao;
import com.study.springboot.dto.UserDto;
@Service
public class UserService implements IUserService{
	
	@Autowired
	IUserDao userdao;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public int join(Map<String, String> map) {
		return userdao.joinDao(map);
	}
	@Override
	public int IDcheck(String userID) {
		int nResult=userdao.IDcheckDao(userID);
		return nResult;
	}
	@Override
	public ArrayList<UserDto> getUserInf(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
		
		return userdao.getUserInfo(userid);
	}
	@Override
	public int modifyUserInfo(Map<String, String> map) {
		System.out.println(map);
		int nResult=userdao.modifyUserInfoDao(map);
		
		System.out.println("update result: "+nResult);
		return nResult;
	}
	@Override
	public int pwCheck(String pw) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();

		String encodedpw=userdao.pwCheckDao(userid);
		System.out.println(encodedpw);
		int nResult=0;
		if(passwordEncoder.matches(pw, encodedpw)) {
			nResult=1;
		}
		
		System.out.println("비밀번호 확인 nResult : "+nResult);
		return nResult;
	}
	
	@Override
	public int profileUpdate(String file) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
        
		int nResult=userdao.profileUpdate(userid, file);
		System.out.println(nResult); 
		return nResult;
	}
	@Override
	public String getProfileImage() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;        
        String userid = userDetails.getUsername();
        
        String profileimage=userdao.getUserProfileDao(userid);
        
        return profileimage;
	}
	@Override
	public String getProfileImage2(String userid) {    
        String profileimage=userdao.getUserProfileDao(userid);
        
        return profileimage;
	}
}
