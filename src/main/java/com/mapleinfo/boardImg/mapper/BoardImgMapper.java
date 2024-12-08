package com.mapleinfo.boardImg.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardImgMapper {

	
	public void insertBoardImg(
			@Param("type") String type,
			@Param("boardId") int boardId,
			@Param("imagePath") String imagePath
			);
	
}
