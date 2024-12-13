package com.mapleinfo.classboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mapleinfo.classboard.domain.ClassBoard;

@Mapper
public interface ClassBoardMapper {

	// 직업 게시판 글 쓰기
//	public int insertClassBoard(
//			@Param("userId") int userId,
//			@Param("characterClass") String characterClass,
//			@Param("subject") String subject,
//			@Param("content") String content,
//			@Param("imagePath") String imagePath
//			);
	
	// summernote로 글쓰기
	public int insertClassBoard(ClassBoard classBoard);
	
	// summernote로 글쓰기 + 이미지주소를 저장된 위치로 변경하기 위해
	public void updateClassBoardForChangePath(
			@Param("type") String type,
			@Param("boardId") int boardId,
			@Param("content") String content
			);
	
	
	
	
	
	// 직업 게시판 글들 가져오기 
	public List<ClassBoard> selectClassBoardList();
	
	// 직업 게시판 글 하나 가져오기 
	public ClassBoard selectClassBoardByClassBoardId(int classBoardId);
	
	
	
	
	// 글 삭제
	public void deleteClassBoard(int classBoardId);
	
	// 글 수정
	public void updateClassBoard(
			@Param("boardId") int boardId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath
			);
	
}
