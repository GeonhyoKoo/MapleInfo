package com.mapleinfo.classboard.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mapleinfo.boardImg.bo.BoardImgBO;
import com.mapleinfo.classboard.domain.ClassBoard;
import com.mapleinfo.classboard.domain.ClassBoardDTO;
import com.mapleinfo.classboard.mapper.ClassBoardMapper;
import com.mapleinfo.comment.bo.CommentBO;
import com.mapleinfo.common.FileManagerService;
import com.mapleinfo.like.bo.LikeBO;
import com.mapleinfo.user.bo.UserBO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClassBoardBO {

	private final ClassBoardMapper classBoardMapper;
	private final FileManagerService fileManager;
	private final UserBO userBO;
	private final LikeBO likeBO;
	private final CommentBO commentBO;
	private final BoardImgBO boardImgBO;
	
	
	
	
	// 기존 자유 게시판 글 작성 
//	public int addClassBoard(int userId, String characterClass, String loginId,  String subject , String content, MultipartFile file) {
//		
//		String imagePath = null;
//		if(file != null) {
//			imagePath = fileManager.uploadFile(loginId, file);
//		}
//		
//		return classBoardMapper.insertClassBoard(userId, characterClass ,subject, content, imagePath);
//	}
	
	
	
	// Summernote 글 작성
	public int addClassBoard(ClassBoard classBoard , String loginId, String[] imgArr) {
		
		
		// 이미지가 없으면
		if (imgArr == null) {
			return classBoardMapper.insertClassBoard(classBoard);			
		} else {
		// 이미지가 있으면
			
			// 1. classBoard에 insert
			classBoardMapper.insertClassBoard(classBoard);
			int boardId = classBoard.getId();
			String type = classBoard.getCharacterClass();
			
			String originPath = null;
			String nowPath = null;
			
			
			
			for (int i = 0; i < imgArr.length; i++) {
				if (i == 0) {
					// 파일 저장
					// /classBoard/abcde_1734008777989/27922e0d-ccee-4b46-9f3a-568161cf3fd6.png
					originPath = fileManager.uploadSummerNoteFileFirst(imgArr[i], loginId);
					
					// BoardImgBO에 insert
					boardImgBO.addBoardImg(type, boardId, originPath);
					
				} else {
					// 파일 저장
					///classBoard/abcde_1734008963483/bee02680-44fe-48ad-a0e5-9c625ae4a401.png
					nowPath = fileManager.uploadSummerNoteFileElse(originPath, imgArr[i]);
					
					// BoardImgBO에 insert
					boardImgBO.addBoardImg(type, boardId, originPath);
				}
			}
			
			
			// content를 temp -> classboard로 시작하는 곳으로 위치 바꾸기
			String content = classBoard.getContent();
			
			String path = null;
			if (originPath != null) {
				String[] pathPart = originPath.split("/");
				
				if(pathPart.length > 2) {
					path = pathPart[2];
				}
			}
			
			if (content != null) {
				content = content.replace("/temp/", "/classBoard/" + path + "/");
			}
			
			// 업데이트
			classBoardMapper.updateClassBoardForChangePath(type , boardId, content);
			
		}
		
		return classBoard.getId();
	}
	
	
	
	
	
	
	// DTO로 변환 단건 
	public ClassBoardDTO generateClassBoard(int classBoardId , int userId) {
		
		ClassBoardDTO classBoard = new ClassBoardDTO();
		
		ClassBoard post = classBoardMapper.selectClassBoardByClassBoardId(classBoardId);
		
		// 유저
		classBoard.setUser(userBO.getUserEntityById(post.getUserId()));
		
		classBoard.setClassboard(post);
		
		String classCharacter = post.getCharacterClass();
		classBoard.setCommentList(commentBO.generateCommentList(classCharacter, classBoardId));
		
		classBoard.setLikeCount(likeBO.getLikeCountByTypeAndBoardId(classCharacter, classBoardId));
		
		classBoard.setLike(likeBO.isLikeByTypeAndBoardIdAndUserId(classCharacter, classBoardId, userId));
		
		return classBoard;
	}
	
	
	// DTO로 변환 리스트
	public List<ClassBoardDTO> generateClassBoardList(){
		
		List<ClassBoardDTO> classBoardList = new ArrayList<>();
		
		List<ClassBoard> list = classBoardMapper.selectClassBoardList();
		
		for(ClassBoard classBoard : list) {
			
			ClassBoardDTO post = new ClassBoardDTO();
			
			// 유저
			int userId = classBoard.getUserId();
			post.setUser(userBO.getUserEntityById(userId));
			
			// 글
			post.setClassboard(classBoard);
			
			classBoardList.add(post);
			
		}
		
		return classBoardList;
	}

	
	// 글 삭제
	public int deleteClassBoard(int userId , String loginId , int boardId) {
		
		ClassBoard post = classBoardMapper.selectClassBoardByClassBoardId(boardId);
		
		if(post == null) {
			return -1;
		}
		
		classBoardMapper.deleteClassBoard(post.getId());
		
		String characterClass = post.getCharacterClass();
		
		commentBO.deleteCommentList(characterClass , boardId);
		
		likeBO.deleteLikeList(characterClass, boardId);
		
//		if(post.getImagePath() != null) {
//			fileManager.deleteFile(post.getImagePath());
//		}
		return 1;
	}
	
		
	// 글 하나만 가져오기 -> updateview에 담기위해
	public ClassBoard getClassBoardByClassBoardId(int boardId) {
		return classBoardMapper.selectClassBoardByClassBoardId(boardId);
	}
	
	
		
	// 글 수정
	public int updateClassBoard(int boardId, String type, int userId, String loginId, String subject, String content, MultipartFile file) {
		
		// 로그인 정보와 일치 안하면 리턴
		ClassBoard classBoard = classBoardMapper.selectClassBoardByClassBoardId(boardId);
		
		if (classBoard == null) {
			return -1;
		}
		
		if(classBoard.getUserId() != userId) {
			return 1;
		}
		
		String imagePath = null;
		
		if (file != null) {
			imagePath =fileManager.uploadFile(loginId, file);
			
//			if(imagePath != null && classBoard.getImagePath() != null) {
//				fileManager.deleteFile(classBoard.getImagePath());
//			}
		}
		
		classBoardMapper.updateClassBoard(boardId, subject, content, imagePath);
		
		return 0;
	}
		
	
	
	
	
}
