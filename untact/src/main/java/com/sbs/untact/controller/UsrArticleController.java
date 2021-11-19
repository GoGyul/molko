package com.sbs.untact.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.util.Util;


@Controller
public class UsrArticleController {
	
	@Autowired
	private ArticleService articleService;

	public UsrArticleController() {
		
	}
	
	@RequestMapping("usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		
		Article article = articleService.getArticle(id);
		
		return article;
	}
	
	@RequestMapping("usr/article/list")
	@ResponseBody
	public List<Article> showList( String searchKeywordType, String searchKeyword){
		System.out.println("searchKeyword ============== "+searchKeyword);
		System.out.println("searchKeywordType ============== "+searchKeywordType);
		if(searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}
		
		if( searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "titleAndContent";
		}
		
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		
		if( searchKeyword != null ) {
			searchKeyword = searchKeyword.trim();
		}
		
		return articleService.getArticles(searchKeywordType, searchKeyword);
		
	}
	
	@RequestMapping("usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param){
		
		if (param.get("title") == null) {
			return new ResultData("f-1","제목을 입력해주세여." );
		}
		
		if (param.get("content") == null) {
			return new ResultData("f-1","내용을 입력해주세요." );
		}

		ResultData rsData = articleService.addArticle(param);
		
		return rsData;

	}
	
	@RequestMapping("usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id){
		
		if(id == null) {
			return new ResultData("f-1","id를 입력해주세요");
		}
		
		Article article = articleService.getArticle(id);
		
		if( article == null) {
			return new ResultData ("f-1", "해당 게시글은 존재하지 않습니다." );
		}
		
		return articleService.deleteArticle(id);
		
	}
	
	
	@RequestMapping("usr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String content){
		
		
		if(id == null) {
			return new ResultData("f-1","id를 입력해주세요");
		}
		if(title == null) {
			return new ResultData("f-1","title를 입력해주세요");
		}
		if(content == null) {
			return new ResultData("f-1","content를 입력해주세요");
		}
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return new ResultData("f-1","해당 게시물은 존재하지않습ㄴ디ㅏ.","id",id);
		}
		
		return articleService.modifyArticle(id,title,content);
			
	}
	
	
}
