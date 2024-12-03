package com.mapleinfo.freeboard.bo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mapleinfo.common.FileManagerService;
import com.mapleinfo.freeboard.domain.FreeBoard;
import com.mapleinfo.freeboard.mapper.FreeBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeBoardBO {

	private final FreeBoardMapper freeBoardMapper;
	private final FileManagerService fileManger;
	
	// 자유 게시판 글 작성
	public int addFreeBoard(int userId, String loginId,  String subject , String content, MultipartFile file) {
		
		String imagePath = null;
		if(file != null) {
			imagePath = fileManger.uploadFile(loginId, file);
		}
		
		return freeBoardMapper.insertFreeBoard(userId, subject, content, imagePath);
	}
	
	
	
	// 자유 게시판 글 가져오기
	public List<FreeBoard> getFreeBoardList(){
		return freeBoardMapper.selectFreeBoardList();
	}
	
	
	// 글 하나 가져오기
	public FreeBoard getFreeBoardByFreeBoardId(int freeBoardId) {
		return freeBoardMapper.selectFreeBoardByFreeBoardId(freeBoardId);
	}
	
	
}
