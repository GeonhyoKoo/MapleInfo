package com.mapleinfo.classboard.domain;

import java.util.List;

import com.mapleinfo.comment.domain.CommentDTO;
import com.mapleinfo.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassBoardDTO {

	private UserEntity user;
	
	private ClassBoard classboard;
	
	private List<CommentDTO> commentList;
	
	private int likeCount;
	
	private boolean isLike;
}
