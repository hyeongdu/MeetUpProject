package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardDto {
	private int tid;
	private String tuserid;
	private String tcategory;
	private String mname;
	private String mtel;
	private String memail;
	private String title;
	private String tintro;
	private String tcontent;
	private String tfile;
	private String tdate;
	private String classstartdate;
	private String classenddate;
	private String regstartdate;
	private String regenddate;
	private String tspacetype;
	private String tspace;
	private int thit;
	private int tgroup;
	private int tmemnum;
	private int tnownum;
	private String payment;
	private int price;
	
	

	
	
}
