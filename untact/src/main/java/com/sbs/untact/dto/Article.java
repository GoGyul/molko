package com.sbs.untact.dto;

import lombok.Data;

@Data
public class Article {

	private int id; 
	private String regDate; 
	private String title; 
	private String content;
	
	public Article(int id, String regDate, String title, String content) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.content = content;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", regDate=" + regDate + ", title=" + title + ", content=" + content + "]";
	}
	
	
	
}
