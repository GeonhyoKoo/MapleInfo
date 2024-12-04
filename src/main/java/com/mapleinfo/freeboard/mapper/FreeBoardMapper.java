package com.mapleinfo.freeboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mapleinfo.freeboard.domain.FreeBoard;

@Mapper
public interface FreeBoardMapper {

	
	// 자유 게시판 글 쓰기
	public int insertFreeBoard(
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath
			);
	
	
	// 자유 게시판 글들 가져오기 
	public List<FreeBoard> selectFreeBoardList();
	
	// 자유 게시판 글 하나 가져오기 
	public FreeBoard selectFreeBoardByFreeBoardId(int freeBoardId);
	
	
	// 글 삭제
	public void deleteFreeBoard(int freeBoardId);
	
	
	// 글 수정
	public void updateFreeBoard(
			@Param("boardId") int boardId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath
			);
	
}
