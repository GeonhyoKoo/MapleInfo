package com.mapleinfo.user.common;

public class IdValidation {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int isRightId(String id) {
		this.id = id;
		
		int result = 1;
		
		for(int i = 0; i < id.length(); i++) {
			if(id.charAt(i) == ' ') {
				return 3;
			}
			if(id.charAt(i) >= 'a' && id.charAt(i) <='z') {
				result = 0;
			}
			
			if(id.charAt(i) == '!' || id.charAt(i) == '@' || id.charAt(i) == '#' || id.charAt(i) == '$' 
					|| id.charAt(i) == '%' || id.charAt(i) == '^' || id.charAt(i) == '&' || id.charAt(i) == '*') {
				return 2;
			}
		}
		
		return result;
	}
	
	
}
