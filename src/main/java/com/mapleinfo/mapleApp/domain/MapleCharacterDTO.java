package com.mapleinfo.mapleApp.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapleCharacterDTO {

	
	private String nickname;
	private int level;
	private String world;
	private String representativeImageUrl;
	private List<ItemEquiment> itemEquiment;
	
	
	@Getter
	@Setter
	public static class ItemEquiment{
		
		private String itemType;
		private String itemName;
		private String itemIcon;
	}
	
	
	
	
	
}
