package com.mapleinfo.character.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mapleinfo.common.FileManagerService;
import com.mapleinfo.common.ImageUpload;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/temp/**") 
		.addResourceLocations("file://" +ImageUpload.FILE_TEMP_UPLOAD_PATH);
		
		registry
		.addResourceHandler("/images/**") 
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); 
		
		registry
		.addResourceHandler("/classBoard/**") 
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_CLASS_BOARD_PATH); 
		
	}
	
}
