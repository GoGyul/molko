package com.sbs.untact.dto;

import java.util.Map;

import com.sbs.untact.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResultData {

	private String resultCode;
	private String msg;
	private Map<String, Object> content;
	
	public ResultData(String resultCode, String msg, Object... args) {
		this.resultCode = resultCode;
		this.msg = msg;
		this.content = Util.mapOf(args);
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("s-");
	}
	
	public boolean isFail() {
		return isSuccess() == false;
	}
	
}
