package com.mapleinfo.freeboard.domain;

import java.util.List;

import com.mapleinfo.comment.domain.CommentDTO;
import com.mapleinfo.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeBoardDTO {
	
	private UserEntity user;
	
	private FreeBoard freeboard;
	
	private List<CommentDTO> commentList;
	
	private int likeCount;
	
	private boolean isLike;
	
	
}
