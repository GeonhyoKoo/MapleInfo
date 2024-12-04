package com.mapleinfo.like.bo;

import org.springframework.stereotype.Service;

import com.mapleinfo.like.mapper.LikeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeBO {

	private final LikeMapper likeMapper;
	
	
	// 좋아요 토글
	public int likeToggle(String type, int boardId, int userId) {
		
		int count = likeMapper.selectLikeCountByTypeAndBoardIdAndUserId(type, boardId, userId);
		if (count == 0) {
			likeMapper.insertLike(type, boardId, userId);
			return 1;
		} else {
			likeMapper.deleteLike(type, boardId, userId);
			return 2;
		}
	}
	
	
	// 좋아요 개수 가져오기(boardId, type)
	public int getLikeCountByTypeAndBoardId(String type, int boardId) {
		return likeMapper.selectLikeCountByTypeAndBoardId(type, boardId);
	}
	
	// 좋아요 여부
	public boolean isLikeByTypeAndBoardIdAndUserId(String type, int boardId, Integer userId) {
		
		if(userId == null) {
			return false;
		}
		
		userId = (int)userId;
		
		int likeCount = likeMapper.selectLikeCountByTypeAndBoardIdAndUserId(type, boardId, userId);
		return likeCount > 0;
	}
	
	// 글 삭제 -> 좋아요 삭제
	public void deleteLikeList(String type , int boardId) {
		likeMapper.deleteLikeList(type, boardId);
	}
	
}
