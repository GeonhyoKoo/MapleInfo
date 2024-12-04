package com.mapleinfo.freeboard.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mapleinfo.comment.bo.CommentBO;
import com.mapleinfo.common.FileManagerService;
import com.mapleinfo.freeboard.domain.FreeBoard;
import com.mapleinfo.freeboard.domain.FreeBoardDTO;
import com.mapleinfo.freeboard.mapper.FreeBoardMapper;
import com.mapleinfo.like.bo.LikeBO;
import com.mapleinfo.user.bo.UserBO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeBoardBO {

	private final FreeBoardMapper freeBoardMapper;
	private final FileManagerService fileManager;
	private final UserBO userBO;
	private final CommentBO commentBO;
	private final LikeBO likeBO;
	
	// 자유 게시판 글 작성
	public int addFreeBoard(int userId, String loginId,  String subject , String content, MultipartFile file) {
		
		String imagePath = null;
		if(file != null) {
			imagePath = fileManager.uploadFile(loginId, file);
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
	
	
	// DTO로 변환 단건 
	public FreeBoardDTO generateFreeBoard(int freeBoardId , int userId) {
		
		FreeBoardDTO freeBoard = new FreeBoardDTO();
		
		FreeBoard post = freeBoardMapper.selectFreeBoardByFreeBoardId(freeBoardId);
		
		// 유저
		freeBoard.setUser(userBO.getUserEntityById(post.getUserId()));
		
		freeBoard.setFreeboard(post);
		
		freeBoard.setCommentList(commentBO.generateCommentList("자유", freeBoardId));
		
		freeBoard.setLikeCount(likeBO.getLikeCountByTypeAndBoardId("자유", freeBoardId));
		
		freeBoard.setLike(likeBO.isLikeByTypeAndBoardIdAndUserId("자유", freeBoardId, userId));
		
		return freeBoard;
	}
	
	
	// DTO로 변환 리스트
	public List<FreeBoardDTO> generateFreeBoardList(){
		
		List<FreeBoardDTO> freeBoardList = new ArrayList<>();
		
		List<FreeBoard> list = freeBoardMapper.selectFreeBoardList();
		
		for(FreeBoard freeBoard : list) {
			
			FreeBoardDTO post = new FreeBoardDTO();
			
			// 유저
			int userId = freeBoard.getUserId();
			post.setUser(userBO.getUserEntityById(userId));
			
			// 글
			post.setFreeboard(freeBoard);
			
			freeBoardList.add(post);
			
		}
		
		return freeBoardList;
	}
	
	
	// 글 삭제
	public int deleteFreeBoard(int userId , String loginId , int boardId) {
		
		FreeBoard post = freeBoardMapper.selectFreeBoardByFreeBoardId(boardId);
		
		if(post == null) {
			return -1;
		}
		
		freeBoardMapper.deleteFreeBoard(post.getId());
		
		commentBO.deleteCommentList("자유" , boardId);
		
		likeBO.deleteLikeList("자유", boardId);
		
		if(post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}
		return 1;
	}
	
	
	
	// 글 수정
	public int updateFreeBoard(int boardId, int userId, String loginId, String subject, String content, MultipartFile file) {
		
		// 로그인 정보와 일치 안하면 리턴
		FreeBoard freeBoard = freeBoardMapper.selectFreeBoardByFreeBoardId(boardId);
		
		if (freeBoard == null) {
			return -1;
		}
		
		if(freeBoard.getUserId() != userId) {
			return 1;
		}
		
		String imagePath = null;
		
		if (file != null) {
			imagePath =fileManager.uploadFile(loginId, file);
			
			if(imagePath != null && freeBoard.getImagePath() != null) {
				fileManager.deleteFile(freeBoard.getImagePath());
			}
		}
		
		freeBoardMapper.updateFreeBoard(boardId, subject, content, imagePath);
		
		return 0;
	}
	
	
}
