package com.mapleinfo.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mapleinfo.comment.domain.Comment;

@Mapper
public interface CommentMapper {

	
	public int addComment(
			@Param("type") String type,
			@Param("boardId") int boardId,
			@Param("userId") int userId,
			@Param("content") String content
			);
	
	
	public List<Comment> selectCommentListByTypeAndBoardId(
			@Param("type") String type,
			@Param("boardId") int boardId
			);
	
	
}
