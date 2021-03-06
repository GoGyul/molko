package com.sbs.untact.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact.dao.MemberDao;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	
	public ResultData join(Map<String, Object> param) {
		memberDao.join(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("s-1", String.format("%s님 환영합니다..", param.get("nickname")), "id",id);
	}

	public Member getMember(int id) {
		return memberDao.getMember(id);
	}
	
	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public ResultData modifyMember(Map<String, Object> param) {
		memberDao.modifyMember(param);
		return new ResultData("s-1","회원정보가 수정되었습니다.");
	}

	// 권한 확인 메서드 actorId == 1 이라면 true(관리자) false라면 일반사용자 처럼 체크를하려함
	public boolean isAdmin(int actorId) {
		return actorId==1;
	}

}
