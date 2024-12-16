package com.mapleinfo.character.config;


public class ConvertToInt {

	
	// stat을 String -> int 로 변환해서 돌려줌
	public int convertStatToInt(String stat) {
		
		int result = 0;
		int[] unitList = {100000000 , 10000};
		
		// 1억 3000만 4021
		stat = stat.trim();
		
		// 억단위 확인
		if (stat.contains("억")) {
			
			// [1,  3000만 4021]
			String[] statParts = stat.split("억");
			if(statParts[0].length() > 0) {
				result += Integer.parseInt(statParts[0]) * unitList[0];
			}
			
			if(statParts.length > 1) {
				stat = statParts[1].trim();
			} else {
				stat = "";
			}
			
		} 
		
		// 만단위 확인
		if (stat.contains("만")) {
			
			String[] statParts = stat.split("만");
			if(statParts[0].length() > 0) {
				result += Integer.parseInt(statParts[0]) * unitList[1];
			}
			
			if(statParts.length > 1) {
				stat = statParts[1].trim();
			} else {
				stat = "";
			}
			
		}
		
		// 나머지 더하기
		if (stat == "") {
			return result;
		}
		result += Integer.parseInt(stat);
		
		return result;
	}
	
	
	
	
}
