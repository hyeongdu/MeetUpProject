package com.study.springboot.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface IPaymentInfoDao {
	public int insertPaymentDao(String userid, int classid, String merchant_id);
	public int checkPayDao(String userid, int classid);
}
