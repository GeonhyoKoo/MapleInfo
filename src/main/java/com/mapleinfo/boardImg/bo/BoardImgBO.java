package com.mapleinfo.boardImg.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mapleinfo.boardImg.domain.BoardImg;
import com.mapleinfo.boardImg.mapper.BoardImgMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardImgBO {
	
	private final BoardImgMapper boardImgMapper;
	
	// 이미지 추가
	public void addBoardImg(String type, int boardId, String imagePath) {
		boardImgMapper.insertBoardImg(type, boardId, imagePath);
	}
	
	
	// 이미지 DTO에 넣기위해
	public List<BoardImg> getBoardImgByBoardId(String type ,int boardId){
		return boardImgMapper.selectBoardImgByBoardId(type , boardId);
	}
	
	// 삭제
	public void deleteBoardImg(String type ,int boardId) {
		boardImgMapper.deleteBoardImg(type , boardId);
	}
	
	
}
