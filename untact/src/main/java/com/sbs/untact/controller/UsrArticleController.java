package com.sbs.untact.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
	public ResultData showDetail(Integer id) {
		if( id == null ) {
			return new ResultData("f-1", "id를 입력해주세요");
		}
		
		Article article = articleService.getForPrintArticle(id);
		
		if (article == null) {
			return new ResultData("f-2", "존재하지 않는 게시물 번호입니다.");
		}
		
		return new ResultData("s-1","성공","article",article);
	}
	
	@RequestMapping("usr/article/list")
	@ResponseBody
	public ResultData showList( String searchKeywordType, String searchKeyword,
								@RequestParam(defaultValue="1") int page){
		
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
		
		if( searchKeyword == null ) {
			searchKeywordType = null;
		}
		
		// 한페이지에 아이템을 몇개를 보여줄것인가.
		int itemsInAPage = 20;
		
		List<Article> articles = articleService.getForPrintArticles(searchKeywordType, searchKeyword, page ,itemsInAPage);
		
		return new ResultData("s-1","성공","articles",articles);
	}
	
	@RequestMapping("usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param, HttpSession session){
		
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);
		
		// getAsInt 메서드를 탓을때 첫번째 매개변수에 값이 있다면 int로 반환되서 
		// loginedMemberId 변수에 담길것이고 값이 없다면 defaultValue 로 지정한
		// 두번쨰 매개변수 자리의 값이 그대로 출력될것이다, 이것을 이용하여 메서드를 구현한다.
//		if(loginedMemberId == 0) {
//			return new ResultData("f-2", "로그인후 이용해주세요");
//		}
		
		if (param.get("title") == null) {
			return new ResultData("f-1","제목을 입력해주세여." );
		}
		
		if (param.get("content") == null) {
			return new ResultData("f-1","내용을 입력해주세요." );
		}
		
		// param에 memberId 를 키값으로 loginedMemberId 를 담아 보낼것이다.
		param.put("memberId", loginedMemberId);

		ResultData rsData = articleService.addArticle(param);
		
		return rsData;

	}
	
	@RequestMapping("usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpSession session){
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId") , 0);
//		if(loginedMemberId == 0 ) {
//			return new ResultData("f-2","로그인후 이용해주세요");
//		}
		
		if(id == null) {
			return new ResultData("f-1","id를 입력해주세요");
		}
		
		Article article = articleService.getArticle(id);
		
		if( article == null) {
			return new ResultData ("f-1", "해당 게시글은 존재하지 않습니다." );
		}
		
		ResultData actorCanDeleteRd = articleService.getActorCanDeleteRd(article, loginedMemberId);
		
		if(actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}
		
		return articleService.deleteArticle(id);
		
	}
	
	
	@RequestMapping("usr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String content, HttpSession session){
		
		// doModify 메서드 역시 session 인증을 거쳐야 함으로 아래 로직으로 세션에 id가 있는지 체크부터함
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);
//		if( loginedMemberId == 0) {
//			return new ResultData("f-2", "로그인후 이용해 주세요");
//		}
		
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
		
		ResultData actorCanModifyRd = articleService.getActorCanModifyRd(article, loginedMemberId);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id,title,content);
			
	}
	
	
}
 