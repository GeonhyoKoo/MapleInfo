package com.mapleinfo.boardImg.bo;

import org.springframework.stereotype.Service;

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
	
	
	
}
