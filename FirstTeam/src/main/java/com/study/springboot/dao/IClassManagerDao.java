package com.study.springboot.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ClassManagerDto;


@Mapper
public interface IClassManagerDao {
	public int countJoinClassDao(String userid);
	public int applyClassDao(Map<String, Object> map);	
	public int quitClassDao(Map<String, Object> map);	
	public int countNowmemDao(int classid);
	public int getClassMemberDao(Map<String, Object> map);
	ArrayList<ClassManagerDto> getApplyClassInfoDao(String userid);
	ArrayList<String> getApplyMemberDao(int classid);
	ArrayList<ClassManagerDto> getClassInfo(int classid);
	public List<ClassManagerDto> locationList(int classid);
	public int updatePaycheck(int classid, String userid);

}
