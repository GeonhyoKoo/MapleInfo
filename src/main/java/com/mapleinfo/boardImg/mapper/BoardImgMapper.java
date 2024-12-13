package com.mapleinfo.boardImg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mapleinfo.boardImg.domain.BoardImg;

@Mapper
public interface BoardImgMapper {

	
	public void insertBoardImg(
			@Param("type") String type,
			@Param("boardId") int boardId,
			@Param("imagePath") String imagePath
			);
	
	
	public List<BoardImg> selectBoardImgByBoardId(
			@Param("type") String type,
			@Param("boardId") int boardId
			);
	
	public void deleteBoardImg(
			@Param("type") String type,
			@Param("boardId") int boardId
			);
}
