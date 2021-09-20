package com.study.springboot.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.UserDto;

@Mapper
public interface IUserDao {
	public int joinDao(Map<String, String> map);
	public int IDcheckDao(String userID);
	public int InsertSNSInfo(Map<String,String> map);
	ArrayList<UserDto> getUserInfo(String userid);
	public int modifyUserInfoDao(Map<String, String> map);
	public String pwCheckDao(String userid);
	public int profileUpdate(String userid, String file);
	public String getUserProfileDao(String userid);
}
