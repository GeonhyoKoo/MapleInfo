package com.mapleinfo.boss.common;

public class ConverterBossNameToImg {

	
	
	public String CovertBossName(String bossName) {
		
		
		switch (bossName) {
		case "루시드" : 
			return "/img/lucid.png";
		case "더스크" :
			return "/img/dusk.png";
		case "진힐라" :
			return "/img/hilla.png";
		case "검은마법사" :
			return "/img/blackmage.png";
		case "듄켈" :
			return "/img/darknell.png";
		case "카링" : 
			return "/img/kaling.png";
		case "칼로스" :
			return "/img/kalos.png";
		case "세렌" : 
			return "/img/seren.png";
		default : 
			return "/img/maplelogo.jpg";
		}
		
	}
	
	
	
}
