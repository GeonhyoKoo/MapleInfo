package com.mapleinfo.freeboard.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mapleinfo.boardImg.bo.BoardImgBO;
import com.mapleinfo.boardImg.domain.BoardImg;
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
	private final BoardImgBO boardImgBO;
	
	// 자유 게시판 글 작성
//	public int addFreeBoard(int userId, String loginId,  String subject , String content, MultipartFile file) {
//		
//		String imagePath = null;
//		FreeBoard freeboard = new FreeBoard();
//		freeboard.setUserId(userId);
//		freeboard.setContent(content);
//		freeboard.setSubject(subject);;
//		freeBoardMapper.insertFreeBoard(freeboard);
//		
//		if(file != null) {
//			imagePath = fileManager.uploadFile(loginId, file);
//			int boardId = freeboard.getId();
//			boardImgBO.addBoardImg("자유" , boardId , imagePath);
//			return 1;
//		} 
//		
//		return 1;
//	}
	
	
	// 자유 게시판 글 작성
	public int addFreeBoard(int userId, String loginId,  String subject , String content, MultipartFile[] files) {
		
		FreeBoard freeboard = new FreeBoard();
		freeboard.setUserId(userId);
		freeboard.setContent(content);
		freeboard.setSubject(subject);;
		freeBoardMapper.insertFreeBoard(freeboard);
		int boardId = freeboard.getId();
		
		String originImagePath = null;
		String imagePath = null;
		if(files != null) {
			
			for (int i = 0; i < files.length; i++) {
				if(i == 0) {
					originImagePath = fileManager.uploadFile(loginId , files[i]);
					boardImgBO.addBoardImg("자유", boardId, originImagePath);
				} else {
				imagePath = fileManager.uploadFileDuplicate(originImagePath, files[i]);
				boardImgBO.addBoardImg("자유" , boardId , imagePath);
				}
			}
			return 1;
		} 
		return 1;
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
		
		freeBoard.setBoardImg(boardImgBO.getBoardImgByBoardId("자유" , post.getId()));
		
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
		List<BoardImg> postImg = boardImgBO.getBoardImgByBoardId("자유" , boardId);
		
		if(post == null) {
			return -1;
		}
		
		freeBoardMapper.deleteFreeBoard(post.getId());
		
		commentBO.deleteCommentList("자유" , boardId);
		
		likeBO.deleteLikeList("자유", boardId);
		
		if(postImg.size() > 0) {
			
			if (postImg.size() == 1) {
				fileManager.deleteFile(postImg.get(0).getImagePath());
			} else {
				for (int i = postImg.size()-1; i >= 0; i--) {
					if (i != 0){
						fileManager.deletePrevFile(postImg.get(i).getImagePath());
					} else {
						fileManager.deleteFile(postImg.get(i).getImagePath());
					}
				}
			}
		
			boardImgBO.deleteBoardImg("자유", boardId);
		}
		return 1;
	}
	
	
	
	// 글 수정
	public int updateFreeBoard(int boardId, int userId, String loginId, String subject, String content, MultipartFile[] files) {
		
		// 로그인 정보와 일치 안하면 리턴
		FreeBoard freeBoard = freeBoardMapper.selectFreeBoardByFreeBoardId(boardId);
		
		if (freeBoard == null) {
			return -1;
		}
		
		if(freeBoard.getUserId() != userId) {
			return 1;
		}
		
		String imagePath = null;
		String originImagePath = null;
		
		if (files != null) {
			
			List<BoardImg> boardImgList = boardImgBO.getBoardImgByBoardId("자유" ,boardId);
			
			if (boardImgList.size() == 0) {
				for (int i = 0; i < files.length; i++) {
					if(i == 0) {
						originImagePath = fileManager.uploadFile(loginId , files[i]);
						boardImgBO.addBoardImg("자유", boardId, originImagePath);
					} else {
						imagePath = fileManager.uploadFileDuplicate(originImagePath, files[i]);
						boardImgBO.addBoardImg("자유" , boardId , imagePath);
					}
				
				}
			
			} else if (boardImgList.size() == 1) {
				
				// 삭제 먼저
				if (boardImgList.get(0).getImagePath() != "") {
					fileManager.deleteFile(boardImgList.get(0).getImagePath());
					boardImgBO.deleteBoardImg("자유", boardId);
				}
				
				// 업로드 이후
				for (int i = 0; i < files.length; i++) {
					if(i == 0) {
						originImagePath = fileManager.uploadFile(loginId , files[i]);
						boardImgBO.addBoardImg("자유", boardId, originImagePath);
					} else {
						imagePath = fileManager.uploadFileDuplicate(originImagePath, files[i]);
						boardImgBO.addBoardImg("자유" , boardId , imagePath);
					}
				
				}
			} else {
				// 먼저 삭제
				for (int i = boardImgList.size()-1; i>=0; i--) {
					if (i != 0) {
						fileManager.deletePrevFile(boardImgList.get(i).getImagePath());
					} else {
						fileManager.deleteFile(boardImgList.get(i).getImagePath());
					}
				}
				
				boardImgBO.deleteBoardImg("자유", boardId);
				
				// 업로드 이후
				for (int i = 0; i < files.length; i++) {
					if(i == 0) {
						originImagePath = fileManager.uploadFile(loginId , files[i]);
						boardImgBO.addBoardImg("자유", boardId, originImagePath);
					} else {
						imagePath = fileManager.uploadFileDuplicate(originImagePath, files[i]);
						boardImgBO.addBoardImg("자유" , boardId , imagePath);
					}
				
				}
				
			}
			freeBoardMapper.updateFreeBoard(boardId, subject, content);
		} 
		
		// 파일 수정시 선택을 안하면 삭제할지?
//		else if(files == null) {
//			
//			List<BoardImg> boardImgList = boardImgBO.getBoardImgByBoardId("자유" ,boardId);
//			
//			if(boardImgList.size() == 0) {
//				freeBoardMapper.updateFreeBoard(boardId, subject, content);
//				return 0;
//			} else if(boardImgList.size() == 1){
//				if (boardImgList.get(0).getImagePath() != "") {
//					fileManager.deleteFile(boardImgList.get(0).getImagePath());
//					boardImgBO.deleteBoardImg("자유", boardId);
//				}
//				freeBoardMapper.updateFreeBoard(boardId, subject, content);
//				return 0;
//			} else {
//				for (int i = boardImgList.size()-1; i>=0; i--) {
//					if (i != 0) {
//						fileManager.deletePrevFile(boardImgList.get(i).getImagePath());
//					} else {
//						fileManager.deleteFile(boardImgList.get(i).getImagePath());
//					}
//				}
//				freeBoardMapper.updateFreeBoard(boardId, subject, content);
//				return 0;
//			}
//		}
		
		freeBoardMapper.updateFreeBoard(boardId, subject, content);
		return 0;
	}
	
	
	
	
}
