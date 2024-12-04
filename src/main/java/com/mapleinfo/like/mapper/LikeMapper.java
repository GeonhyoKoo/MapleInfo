package com.mapleinfo.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

	
	public int selectLikeCountByTypeAndBoardIdAndUserId(
			@Param("type") String type,
			@Param("boardId") int boardId,
			@Param("userId") int userId
			);
	
	
	public void insertLike(
			@Param("type") String type,
			@Param("boardId") int boardId,
			@Param("userId") int userId
			);
	
	
	public void deleteLike(
			@Param("type") String type,
			@Param("boardId") int boardId,
			@Param("userId") int userId
			);
	
	
	public int selectLikeCountByTypeAndBoardId(
			@Param("type") String type,
			@Param("boardId") int boardId
			);
	
	
	public void deleteLikeList(
			@Param("type") String type,
			@Param("boardId") int boardId
			);
	
}
