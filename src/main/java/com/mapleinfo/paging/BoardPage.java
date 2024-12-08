package com.mapleinfo.paging;


public class BoardPage {

	// 현재 페이지
	private int pageNum;
	
	// 페이지당 보여줄 게시물 갯수
	private int amount;
	
	// 스킵할 게시물 수  (pageNum -1) * amount 
	private int skip;
	
	// 기본 생성자 -> 페이지 1 양 정의
	public BoardPage() {
		this(1, 20);
		this.skip = 0;
	}

	// 값을 넘겨받을 땐 세팅
	public BoardPage(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum -1) * amount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		
		this.skip = (pageNum - 1) * this.amount;
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		
		this.skip = (this.pageNum - 1) * amount;
		this.amount = amount;
	}
	
	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	@Override
	public String toString() {
		return "BoardPage [pageNum = " + pageNum + " , amount = " + amount + ", skip = " + skip + "]";
	}
	
	
}
