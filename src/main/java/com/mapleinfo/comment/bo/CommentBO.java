
package com.mapleinfo.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mapleinfo.comment.domain.Comment;
import com.mapleinfo.comment.domain.CommentDTO;
import com.mapleinfo.comment.mapper.CommentMapper;
import com.mapleinfo.user.bo.UserBO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentBO {

	private final CommentMapper commentMapper;
	private final UserBO userBO;
	
	/**
	 * 댓글 작성
	 * @param type
	 * @param boardId
	 * @param userId
	 * @param content
	 * @return
	 */
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
	
	
	/**
	 * comment -> comment DTO변환
	 * @param type
	 * @param boardId
	 * @return
	 */
	public List<CommentDTO> generateCommentList(String type , int boardId){
		
		List<CommentDTO> commentDTOList = new ArrayList<>();
		
		List<Comment> commentList = commentMapper.selectCommentListByTypeAndBoardId(type, boardId);
		
		for (Comment comment : commentList) {
			CommentDTO commentDTO = new CommentDTO();
			
			commentDTO.setComment(comment);
			
			int userId = comment.getUserId();
			commentDTO.setUser(userBO.getUserEntityById(userId));
			
			commentDTOList.add(commentDTO);
		}
		
		return commentDTOList;
		
	}
	
	
	// 댓글 삭제 - 단건
	public int deleteComment(int userId , int commentId) {
		
		Comment comment = commentMapper.selectComment(commentId);
		if(userId != comment.getUserId()) {
			return -1;
		}
		
		commentMapper.deleteComment(commentId);
		
		return 1;
	}
	
	
	// 글 삭제 -> 댓글 삭제
	public void deleteCommentList(String type , int boardId) {
		commentMapper.deleteCommentList(type, boardId);
	}
	
}
