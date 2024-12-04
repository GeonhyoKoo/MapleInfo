package com.mapleinfo.comment.domain;

import com.mapleinfo.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
	
	private Comment comment;
	private UserEntity user;
}
