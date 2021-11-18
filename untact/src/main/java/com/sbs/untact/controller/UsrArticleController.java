package com.sbs.untact.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;

@Controller
public class UsrArticleController {

	private int articlesLastId;
	
	private List<Article> articles;
	
	public UsrArticleController() {
		
		articlesLastId = 0;
		
		articles = new ArrayList<Article>();
		
		Article a1 = new Article(++articlesLastId, "2020-12-12", "제목","내용");
		Article a2 = new Article(++articlesLastId, "2020-12-12", "제목","내용");
		Article a3 = new Article(++articlesLastId, "2020-12-12", "제목","내용");
		
		articles.add(a1);
		articles.add(a2);
		articles.add(a3);
		
	}
	
	@RequestMapping("usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		
		return articles.get(id);
	}
	
	@RequestMapping("usr/article/list")
	@ResponseBody
	public List<Article> showAll(){
		
		return articles;
		
	}
	
	@RequestMapping("usr/article/doAdd")
	@ResponseBody
	public Map<String, Object> doAdd(String regDate, String title, String content){
		articles.add(new Article(++articlesLastId, regDate, title, content));
		
		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "s-1");
		rs.put("msg", "성공");
		rs.put("id", articlesLastId);
		
		return rs;
	}
		
}