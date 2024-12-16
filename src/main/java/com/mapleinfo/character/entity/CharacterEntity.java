package com.mapleinfo.character.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Getter
@Table(name="myCharacter")
@Entity
public class CharacterEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	@Column(name ="userId")
	private int userId;
	private String name;
	@Column(name = "characterClass")
	private String characterClass;
	private String world;
	private int level;
	private String stat;
	@Column(name = "originStat")
	private int originStat;
	@Column(name = "characterImg")
	private String characterImg;
	@Column(name = "representCharacter")
	private boolean representCharacter;
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime updatedAt;
	
}
