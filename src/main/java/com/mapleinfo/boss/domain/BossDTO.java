package com.mapleinfo.boss.domain;

import java.util.List;

import com.mapleinfo.bossMember.domain.BossMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BossDTO {

	
	private Boss boss;
	
	private List<BossMember> bossMember;
	
}
