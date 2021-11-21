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
	
	@Autowired
	private MemberService memberService;

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

//	======================= getActorCanModifyRd () 메서드 ======================
	
	public ResultData getActorCanModifyRd(Article article, int actorId) {
		
		if(article.getMemberId() == actorId ) {
			return new ResultData("s-1","가능합니다.");
		}
		
		if (memberService.isAdmin(actorId)) {
			return new ResultData("s-2","가능합니다.");
		}
		
		return new ResultData("f-1", "권한이 없습니다.");
	}
	
	public ResultData getActorCanDeleteRd(Article article, int actorId) {
		return getActorCanModifyRd(article, actorId);
	}

	public Article getForPrintArticle(int id) {
		return articleDao.getForPrintArticle(id);
	}

	public List<Article> getForPrintArticles(String searchKeywordType, String searchKeyword, int page, int itemsInAPage) {
		
		/*
		 	만약 한 페이지당 10개의 게시물이 출력되도록 하고 싶다면
		 	매개변수로 받아온 page는 현재 위치하고 있는 페이지가 될것이고
		 	itemsInAPage 는 한 페이지당 보여줄 게시물 수가 될것이다.
		 	limitStart 와 limitTake 의 관계는
		 	LIMIT 
		 	0 , 10
		 	1 , 10
		 	2 , 10 처럼 동작한다?
		 */
		int limitStart = (page-1)*itemsInAPage;
		int limitTake = itemsInAPage;
		
		return articleDao.getForPrintArticles(searchKeywordType, searchKeyword, limitStart, limitTake);
	}
	
}
