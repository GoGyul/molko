package com.sbs.untact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact.dao.ArticleDao;
import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;

	public Article getArticle(int id) {
		
		return articleDao.getArticle(id);

	}

	public List<Article> getArticles(String searchKeywordType, String searchKeyword){
		
		System.out.println("service searchKeywordType========"+ searchKeywordType);
		System.out.println("service searchKeyword========"+ searchKeyword);
		
		return articleDao.getArticles(searchKeywordType, searchKeyword);
		
	}
	
	public ResultData addArticle(Map<String, Object> param) {
		
		articleDao.addArticle(param);
		
		int id = Util.getAsInt(param.get("id"),0);
		
		return new ResultData("s-1","성공","id",id);
	}
	
	
	
	
	public ResultData deleteArticle(int id) {
		
		articleDao.deleteArticle(id);
		
		return new ResultData("s-1","삭제하였습니다..","id",id);
	}

	
	
	public ResultData modifyArticle(int id, String title, String content) {
		
		articleDao.modifyArticle(id, title, content);
		
		return new ResultData("s-1","수정에 성공하였습니다.","id",id);
	}
	
}