package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ClassLikeManagerDto;

@Mapper
public interface IClassLikeManagerDao {
	public int doLikeDao(int classid, String classtitle, String userid);
	public int likeCountDao(int classid);
	public int likeCheckDao(int classid, String userid);
	public int undoLikeDao(int classid, String userid);
	public int userLikeCountCheckDao(String userid);
	ArrayList<ClassLikeManagerDto> getLikeClassInfoDao(String userid);
}
