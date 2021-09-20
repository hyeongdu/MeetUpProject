package com.study.springboot.service;

import java.util.ArrayList;
import java.util.Map;

import com.study.springboot.dto.UserDto;

public interface IUserService {
	public int join(Map<String, String> map);
	public int IDcheck(String userID);
	ArrayList<UserDto> getUserInf();
	public int modifyUserInfo(Map<String, String> map);
	public int pwCheck(String pw);
	public int profileUpdate(String file);
	public String getProfileImage();
	public String getProfileImage2(String userid);
}
