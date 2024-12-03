package com.mapleinfo.comment.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mapleinfo.comment.domain.Comment;
import com.mapleinfo.comment.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentBO {

	private final CommentMapper commentMapper;
	
	// 댓글 작성
	public int addComment(String type, int boardId, int userId, String content) {
		
		if(type == null) {
			type = "자유";
		}
		
		return commentMapper.addComment(type, boardId, userId, content);
	}
	
	
	// 댓글 가져오기
	public List<Comment> getCommentListByTypeAndBoardId(String type , int boardId){
		
		List<Comment> commentList = commentMapper.selectCommentListByTypeAndBoardId(type, boardId);
		return commentList;
	}
	
	
}
