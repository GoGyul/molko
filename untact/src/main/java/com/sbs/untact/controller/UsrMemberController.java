package com.sbs.untact.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.MemberService;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;

	@RequestMapping("usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(@RequestParam Map<String, Object> param) {
		
		if(param.get("loginId") == null) {
			return new ResultData("f-1", "loginId 를 입력해주세요");
		}
		
		//	doJoin() 메서드에서 파라미터로 들어온 param 이라는 변수의 loginId를 꺼내서
		//	MemberService 클래스의 getMember 메서드의 매개변수로 다시 넣어준다.
		Member existingMember = memberService.getMemberByLoginId((String)param.get("loginId"));
		
//		if( existingMember != null) {
//			return new ResultData("f-2", String.format("%s 는 이미 사용중인 로그인 아이디입니다.", param.get("loginId")));
//		}
		
		if(param.get("loginPw") == null) {
			return new ResultData("f-1", "loginPw 를 입력해주세요"); 
		}
		
		if(param.get("name") == null) {
			return new ResultData("f-1", "name을 입력해주세요"); 
		}
		
		if(param.get("nickname") == null) {
			return new ResultData("f-1", "nickname을 입력해주세요"); 
		}
		
		if(param.get("cellphoneNo") == null) {
			return new ResultData("f-1", "cellphoneNo을 입력해주세요"); 
		}
		
		if(param.get("email") == null) {
			return new ResultData("f-1", "email을 입력해주세요"); 
		}
		return memberService.join(param);
	}
	
	@RequestMapping("usr/member/doLogin")
	@ResponseBody
	public ResultData doLogin(String loginId, String loginPw, HttpServletRequest req) {
		
		HttpSession session = req.getSession();

//		if( session.getAttribute("loginedMemberId") != null ) {
//			return new ResultData("f-4", "이미 로그인 되었습니다.");
//		}
		
		if(loginId == null ) {
			return new ResultData("f-1", "loginId를 입력해주세요");
		}
	
		Member existingMember = memberService.getMemberByLoginId(loginId);
		
		if(existingMember == null) {
			return new ResultData("f-2","존재하지 않는 로그인 아이디입니다.","loginId", loginId);
		}
		
		if(loginPw == null ) {
			return new ResultData("f-1", "loginPw를 입력해주세요");
		}
		
		if(existingMember.getLoginPw().equals(loginPw) == false) {
			return new ResultData("f-3", "비밀번호가 일치하지 않습니다");
		}
		
		
		session.setAttribute("loginedMemberId", existingMember.getId());
		
		return new ResultData("s-1", String.format("%s 님 환영합니다.",existingMember.getNickname()));
		
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		// 이미 세션에 값이 없으면 처리해줄 로직
//		if(session.getAttribute("loginedMemberId") == null) {
//			return new ResultData("s-2", "이미 로그아웃 되었습니다.");
//		}
		
		//세션에 있는 값을 removeAttribute 메서드를 사용해 제거
		session.removeAttribute("loginedMemberId");
		
		return new ResultData("s-1","로그아웃되었습니다.");
		
	}
	
	@RequestMapping("usr/member/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param ,HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		// 세션에 로그인이 안되있다면 처리
//		if(session.getAttribute("loginedMemberId") == null) {
//			return new ResultData("f-1", "로그인 후 이용해주세요");
//		}
		
		if( param.isEmpty()) {
			return new ResultData("f-2", "수정할 정보를 입력해주세요");
		}
		
		//	세션에 담긴 loginId 를 변수로 담음
		int loginedMemberId = (int)session.getAttribute("loginedMemberId");
		//	param (Map) 에 memberId 라는 키로 loginedMemberId 를 밸류로 담은뒤
		param.put("id", loginedMemberId);
		
		//	modifyMember() 메서드의 매개변수로 param을 보내줌
		return memberService.modifyMember(param);
		
	}
	
	
	
	
}
