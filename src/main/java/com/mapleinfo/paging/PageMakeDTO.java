package com.mapleinfo.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageMakeDTO {

	// 시작
	private int startPage;
	
	// 끝
	private int endPage;
	
	// 이전 , 다음 페이지 유무
	private boolean prev;
	private boolean next;
	
	// 총 게시물
	private int totalPost;
	
	//페이지당 게시물 표시 정보
	
	
	
}
