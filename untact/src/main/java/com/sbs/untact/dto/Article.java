package com.sbs.untact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

	private int id; 
	private String regDate; 
	private String updateDate;
	private int memberId;
	private String title; 
	private String content;
	
	private String extra__writer;

}
